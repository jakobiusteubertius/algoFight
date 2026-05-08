package com.algofight

import android.app.Activity
import android.os.Bundle
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
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            addView(title)
            addView(stepLabel)
            addView(prompt)
            addView(action)
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
