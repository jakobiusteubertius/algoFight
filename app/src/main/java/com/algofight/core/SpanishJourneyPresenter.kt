package com.algofight.core

class SpanishJourneyPresenter {
    fun present(journey: SpanishTrainingJourney): SpanishJourneyViewState {
        val stepIndex = SpanishTrainingStep.entries.indexOf(journey.currentStep) + 1
        val stepLabel = if (journey.isComplete) {
            "Ready"
        } else {
            "Step $stepIndex of ${SpanishTrainingStep.entries.size}"
        }

        return SpanishJourneyViewState(
            title = "Spanish TikTok Trainer",
            stepLabel = stepLabel,
            prompt = journey.currentPrompt,
            primaryAction = if (journey.isComplete) "Start learning batch" else "Mark step done",
        )
    }
}
