package com.algofight

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.algofight.core.SpanishJourneyPresenter
import com.algofight.core.SpanishTrainingJourney

class MainActivity : Activity() {
    private lateinit var store: JourneyStore
    private val presenter = SpanishJourneyPresenter()
    private lateinit var journey: SpanishTrainingJourney
    private lateinit var stepLabel: TextView
    private lateinit var prompt: TextView
    private lateinit var action: Button
    private lateinit var overlayPermission: Button
    private lateinit var usagePermission: Button
    private lateinit var overlaySession: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = JourneyStore(this)
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
    }
}
