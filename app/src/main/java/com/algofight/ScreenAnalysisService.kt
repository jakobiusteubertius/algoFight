package com.algofight

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import com.algofight.core.ScreenAnalysisPolicy
import com.algofight.core.SpanishLearningClassifier
import com.algofight.core.TikTokCropPlanner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.nio.ByteBuffer

class ScreenAnalysisService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private val analysisPolicy = ScreenAnalysisPolicy()
    private val cropPlanner = TikTokCropPlanner()
    private val classifier = SpanishLearningClassifier()
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private lateinit var foregroundPackageReader: ForegroundPackageReader
    private lateinit var analysisStore: AnalysisStore
    private var mediaProjection: MediaProjection? = null
    private var imageReader: ImageReader? = null
    private var processing = false
    private var sessionActive = false

    private val sampleScreen = object : Runnable {
        override fun run() {
            sampleIfAllowed()
            handler.postDelayed(this, SAMPLE_INTERVAL_MS)
        }
    }

    override fun onCreate() {
        super.onCreate()
        foregroundPackageReader = ForegroundPackageReader(this)
        analysisStore = AnalysisStore(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, notification())
        val resultCode = intent?.getIntExtra(EXTRA_RESULT_CODE, Activity.RESULT_CANCELED)
            ?: Activity.RESULT_CANCELED
        val data = intent?.getParcelableExtra<Intent>(EXTRA_DATA)
        if (resultCode != Activity.RESULT_OK || data == null) {
            stopSelf()
            return START_NOT_STICKY
        }

        sessionActive = true
        val projectionManager = getSystemService(MediaProjectionManager::class.java)
        mediaProjection = projectionManager.getMediaProjection(resultCode, data)
        startVirtualDisplay()
        handler.removeCallbacks(sampleScreen)
        handler.post(sampleScreen)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        handler.removeCallbacks(sampleScreen)
        imageReader?.close()
        mediaProjection?.stop()
        recognizer.close()
        super.onDestroy()
    }

    private fun startVirtualDisplay() {
        val metrics = resources.displayMetrics
        imageReader = ImageReader.newInstance(
            metrics.widthPixels,
            metrics.heightPixels,
            PixelFormat.RGBA_8888,
            MAX_IMAGES,
        )
        mediaProjection?.createVirtualDisplay(
            "algoFight-screen-analysis",
            metrics.widthPixels,
            metrics.heightPixels,
            metrics.densityDpi,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader?.surface,
            null,
            handler,
        )
    }

    private fun sampleIfAllowed() {
        val decision = analysisPolicy.evaluate(
            hasMediaProjectionConsent = mediaProjection != null,
            sessionActive = sessionActive,
            foregroundPackage = foregroundPackageReader.currentPackage(),
        )
        if (!decision.allowed || processing) {
            return
        }

        val image = imageReader?.acquireLatestImage() ?: return
        processing = true
        val bitmap = image.toBitmap()
        image.close()
        analyzeBitmap(bitmap)
    }

    private fun analyzeBitmap(bitmap: Bitmap) {
        cropPlanner.regionsFor(bitmap.width, bitmap.height).forEach { region ->
            val crop = Bitmap.createBitmap(
                bitmap,
                region.left,
                region.top,
                region.width(),
                region.height(),
            )
            recognizer.process(InputImage.fromBitmap(crop, 0))
                .addOnSuccessListener { text ->
                    val result = classifier.classify(text.text)
                    analysisStore.recordDecision(result.color)
                    Log.d(TAG, "region=${region.name} color=${result.color} reasons=${result.reasons}")
                }
                .addOnFailureListener { error ->
                    Log.w(TAG, "OCR failed for ${region.name}", error)
                }
                .addOnCompleteListener {
                    crop.recycle()
                }
        }
        bitmap.recycle()
        processing = false
    }

    private fun Image.toBitmap(): Bitmap {
        val plane = planes[0]
        val buffer: ByteBuffer = plane.buffer
        val pixelStride = plane.pixelStride
        val rowStride = plane.rowStride
        val rowPadding = rowStride - pixelStride * width
        val bitmap = Bitmap.createBitmap(
            width + rowPadding / pixelStride,
            height,
            Bitmap.Config.ARGB_8888,
        )
        bitmap.copyPixelsFromBuffer(buffer)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height).also {
            bitmap.recycle()
        }
    }

    private fun createNotificationChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                getString(R.string.screen_analysis_channel),
                NotificationManager.IMPORTANCE_LOW,
            ),
        )
    }

    private fun notification(): Notification {
        val intent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE,
        )
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.screen_analysis_notification_title))
            .setContentText(getString(R.string.screen_analysis_notification_text))
            .setSmallIcon(android.R.drawable.ic_menu_view)
            .setContentIntent(intent)
            .build()
    }

    companion object {
        private const val TAG = "ScreenAnalysisService"
        private const val CHANNEL_ID = "screen-analysis"
        private const val NOTIFICATION_ID = 1002
        private const val SAMPLE_INTERVAL_MS = 5_000L
        private const val MAX_IMAGES = 2
        private const val EXTRA_RESULT_CODE = "resultCode"
        private const val EXTRA_DATA = "data"

        fun startIntent(context: Context, resultCode: Int, data: Intent): Intent =
            Intent(context, ScreenAnalysisService::class.java)
                .putExtra(EXTRA_RESULT_CODE, resultCode)
                .putExtra(EXTRA_DATA, data)
    }
}
