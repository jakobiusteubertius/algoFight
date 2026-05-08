package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SpanishTrainingJourneyTest {
    @Test
    fun startsWithFreshAccountStepAndPrompt() {
        val journey = SpanishTrainingJourney()

        assertThat(journey.currentStep).isEqualTo(SpanishTrainingStep.FreshAccount)
        assertThat(journey.currentPrompt).contains("fresh TikTok account")
    }

    @Test
    fun advancesOneStepWhenCurrentStepIsCompleted() {
        val journey = SpanishTrainingJourney()
            .completeCurrentStep()

        assertThat(journey.currentStep).isEqualTo(SpanishTrainingStep.FollowCreators)
        assertThat(journey.currentPrompt).contains("Follow")
    }

    @Test
    fun keepsCompletedStepsAfterSerializationRoundTrip() {
        val journey = SpanishTrainingJourney()
            .completeCurrentStep()
            .completeCurrentStep()

        val restored = SpanishTrainingJourney.restore(journey.snapshot())

        assertThat(restored.completedSteps).containsExactly(
            SpanishTrainingStep.FreshAccount,
            SpanishTrainingStep.FollowCreators,
        )
        assertThat(restored.currentStep).isEqualTo(SpanishTrainingStep.SearchTerms)
    }

    @Test
    fun initialTrainingBatchDefaultsToTenVideos() {
        val journey = SpanishTrainingJourney()

        assertThat(journey.initialTrainingBatchSize).isEqualTo(10)
    }

    @Test
    fun remainsOnFirstLearningBatchAfterAllStepsComplete() {
        val journey = SpanishTrainingStep.entries.fold(SpanishTrainingJourney()) { current, _ ->
            current.completeCurrentStep()
        }

        assertThat(journey.currentStep).isEqualTo(SpanishTrainingStep.FirstLearningBatch)
        assertThat(journey.isComplete).isTrue()
    }
}
