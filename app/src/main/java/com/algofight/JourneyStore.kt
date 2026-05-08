package com.algofight

import android.content.Context
import com.algofight.core.SpanishTrainingJourney

class JourneyStore(context: Context) {
    private val preferences = context.getSharedPreferences("journey", Context.MODE_PRIVATE)

    fun load(): SpanishTrainingJourney =
        SpanishTrainingJourney.restore(preferences.getString(KEY_SPANISH_JOURNEY, null).orEmpty())

    fun save(journey: SpanishTrainingJourney) {
        preferences.edit()
            .putString(KEY_SPANISH_JOURNEY, journey.snapshot())
            .apply()
    }

    private companion object {
        const val KEY_SPANISH_JOURNEY = "spanish_journey_snapshot"
    }
}
