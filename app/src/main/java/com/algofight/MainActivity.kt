package com.algofight

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.algofight.core.SpanishJourneyPresenter
import com.algofight.core.SpanishTrainingJourney
import com.algofight.core.BatchFeedback

class MainActivity : Activity() {
    private lateinit var store: JourneyStore
    private lateinit var analysisStore: AnalysisStore
    private val presenter = SpanishJourneyPresenter()
    private lateinit var journey: SpanishTrainingJourney
    private lateinit var stepLabel: TextView
    private lateinit var prompt: TextView
    private lateinit var action: Button
    private lateinit var overlayPermission: Button
    private lateinit var usagePermission: Button
    private lateinit var overlaySession: Button
    private lateinit var screenAnalysis: Button
    private lateinit var feedbackPrompt: TextView
    private lateinit var mostlySpanish: Button
    private lateinit var mixed: Button
    private lateinit var mostlyJunk: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = JourneyStore(this)
        analysisStore = AnalysisStore(this)
        journey = store.load()

        val title = TextView(this).apply {
            textSize = 28f
        }
        stepLabel = TextView(this).apply {
            textSize = 14f
        }
        prompt = TextView(this).apply {
            textSize = 16f
        }
        action = Button(this).apply {
            setOnClickListener {
                journey = journey.completeCurrentStep()
                store.save(journey)
                render()
            }
        }
        overlayPermission = Button(this).apply {
            text = getString(R.string.allow_overlay)
            setOnClickListener {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName"),
                    ),
                )
            }
        }
        usagePermission = Button(this).apply {
            text = getString(R.string.allow_usage_access)
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
            }
        }
        overlaySession = Button(this).apply {
            text = getString(R.string.start_overlay_session)
            setOnClickListener {
                startService(OverlayFrameService.startIntent(this@MainActivity))
            }
        }
        screenAnalysis = Button(this).apply {
            text = getString(R.string.start_screen_analysis)
            setOnClickListener {
                val projectionManager = getSystemService(MediaProjectionManager::class.java)
                startActivityForResult(
                    projectionManager.createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION,
                )
            }
        }
        feedbackPrompt = TextView(this).apply {
            textSize = 14f
        }
        mostlySpanish = feedbackButton(R.string.feedback_mostly_spanish, BatchFeedback.MostlySpanish)
        mixed = feedbackButton(R.string.feedback_mixed, BatchFeedback.Mixed)
        mostlyJunk = feedbackButton(R.string.feedback_mostly_junk, BatchFeedback.MostlyJunk)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            addView(title)
            addView(stepLabel)
            addView(prompt)
            addView(action)
            addView(overlayPermission)
            addView(usagePermission)
            addView(overlaySession)
            addView(screenAnalysis)
            addView(feedbackPrompt)
            addView(mostlySpanish)
            addView(mixed)
            addView(mostlyJunk)
        }

        setContentView(layout)
        title.text = getString(R.string.app_name)
        render()
    }

    private fun render() {
        val state = presenter.present(journey)
        stepLabel.text = state.stepLabel
        prompt.text = state.prompt
        action.text = state.primaryAction
        renderFeedback()
    }

    private fun feedbackButton(label: Int, feedback: BatchFeedback): Button =
        Button(this).apply {
            text = getString(label)
            setOnClickListener {
                analysisStore.recordFeedback(feedback)
                renderFeedback()
            }
        }

    private fun renderFeedback() {
        val pending = analysisStore.pendingFeedback()
        val visible = pending != null
        feedbackPrompt.text = pending?.summary.orEmpty()
        feedbackPrompt.visibility = if (visible) TextView.VISIBLE else TextView.GONE
        mostlySpanish.visibility = if (visible) Button.VISIBLE else Button.GONE
        mixed.visibility = if (visible) Button.VISIBLE else Button.GONE
        mostlyJunk.visibility = if (visible) Button.VISIBLE else Button.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK && data != null) {
            startForegroundService(
                ScreenAnalysisService.startIntent(
                    context = this,
                    resultCode = resultCode,
                    data = data,
                ),
            )
        }
    }

    private companion object {
        const val REQUEST_MEDIA_PROJECTION = 1001
    }
}
