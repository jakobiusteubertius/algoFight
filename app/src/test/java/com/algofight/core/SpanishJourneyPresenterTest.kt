package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SpanishJourneyPresenterTest {
    @Test
    fun createsViewStateForCurrentJourneyStep() {
        val state = SpanishJourneyPresenter().present(SpanishTrainingJourney())

        assertThat(state.title).isEqualTo("Spanish TikTok Trainer")
        assertThat(state.stepLabel).isEqualTo("Step 1 of 6")
        assertThat(state.prompt).contains("fresh TikTok account")
        assertThat(state.primaryAction).isEqualTo("Mark step done")
    }

    @Test
    fun showsLearningBatchActionWhenJourneyIsComplete() {
        val completeJourney = SpanishTrainingStep.entries.fold(SpanishTrainingJourney()) { journey, _ ->
            journey.completeCurrentStep()
        }

        val state = SpanishJourneyPresenter().present(completeJourney)

        assertThat(state.stepLabel).isEqualTo("Ready")
        assertThat(state.primaryAction).isEqualTo("Start learning batch")
    }
}
