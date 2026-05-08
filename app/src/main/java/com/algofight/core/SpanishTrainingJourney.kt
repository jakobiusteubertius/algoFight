package com.algofight.core

data class SpanishTrainingJourney(
    val completedSteps: Set<SpanishTrainingStep> = emptySet(),
    val initialTrainingBatchSize: Int = DEFAULT_INITIAL_TRAINING_BATCH_SIZE,
) {
    val currentStep: SpanishTrainingStep
        get() = SpanishTrainingStep.entries.firstOrNull { it !in completedSteps }
            ?: SpanishTrainingStep.FirstLearningBatch

    val currentPrompt: String
        get() = currentStep.prompt

    val isComplete: Boolean
        get() = completedSteps.containsAll(SpanishTrainingStep.entries)

    fun completeCurrentStep(): SpanishTrainingJourney =
        copy(completedSteps = completedSteps + currentStep)

    fun snapshot(): String =
        completedSteps.joinToString(separator = ",") { it.name }

    companion object {
        const val DEFAULT_INITIAL_TRAINING_BATCH_SIZE = 10

        fun restore(snapshot: String): SpanishTrainingJourney {
            val completed = snapshot
                .split(',')
                .mapNotNull { raw ->
                    raw.trim()
                        .takeIf { it.isNotBlank() }
                        ?.let { runCatching { SpanishTrainingStep.valueOf(it) }.getOrNull() }
                }
                .toSet()

            return SpanishTrainingJourney(completedSteps = completed)
        }
    }
}
