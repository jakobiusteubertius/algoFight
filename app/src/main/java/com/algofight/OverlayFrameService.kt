package com.algofight

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.algofight.core.FrameColor
import com.algofight.core.OverlayActivationPolicy

class OverlayFrameService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private val activationPolicy = OverlayActivationPolicy()
    private lateinit var foregroundPackageReader: ForegroundPackageReader
    private lateinit var analysisStore: AnalysisStore
    private var windowManager: WindowManager? = null
    private var frameView: View? = null
    private var sessionActive = false

    private val pollForegroundApp = object : Runnable {
        override fun run() {
            updateOverlayVisibility()
            handler.postDelayed(this, POLL_INTERVAL_MS)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        foregroundPackageReader = ForegroundPackageReader(this)
        analysisStore = AnalysisStore(this)
        when (intent?.action) {
            ACTION_STOP -> {
                stopSelf()
                return START_NOT_STICKY
            }
            else -> {
                sessionActive = true
                ensureFrame()
                handler.removeCallbacks(pollForegroundApp)
                handler.post(pollForegroundApp)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        handler.removeCallbacks(pollForegroundApp)
        removeFrame()
        super.onDestroy()
    }

    private fun ensureFrame() {
        if (!Settings.canDrawOverlays(this) || frameView != null) {
            return
        }

        windowManager = getSystemService(WindowManager::class.java)
        val frame = FrameLayout(this).apply {
            background = frameDrawable(FrameColor.Grey)
            visibility = View.GONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT,
        ).apply {
            gravity = Gravity.TOP or Gravity.START
        }

        windowManager?.addView(frame, params)
        frameView = frame
    }

    private fun removeFrame() {
        frameView?.let { view ->
            runCatching { windowManager?.removeView(view) }
        }
        frameView = null
    }

    private fun updateOverlayVisibility() {
        val result = activationPolicy.evaluate(
            sessionActive = sessionActive,
            foregroundPackage = foregroundPackageReader.currentPackage(),
        )

        frameView?.background = frameDrawable(analysisStore.latestFrameColor())
        frameView?.visibility = if (result.visible) View.VISIBLE else View.GONE
    }

    private fun frameDrawable(color: FrameColor): GradientDrawable =
        GradientDrawable().apply {
            setColor(Color.TRANSPARENT)
            setStroke(FRAME_WIDTH_PX, color.toArgb())
        }

    private fun FrameColor.toArgb(): Int =
        when (this) {
            FrameColor.Grey -> Color.rgb(128, 128, 128)
            FrameColor.Green -> Color.rgb(0, 190, 90)
            FrameColor.Red -> Color.rgb(220, 48, 48)
        }

    companion object {
        private const val ACTION_STOP = "com.algofight.action.STOP_OVERLAY"
        private const val POLL_INTERVAL_MS = 1_000L
        private const val FRAME_WIDTH_PX = 10

        fun startIntent(context: Context): Intent =
            Intent(context, OverlayFrameService::class.java)

        fun stopIntent(context: Context): Intent =
            Intent(context, OverlayFrameService::class.java).setAction(ACTION_STOP)
    }
}
