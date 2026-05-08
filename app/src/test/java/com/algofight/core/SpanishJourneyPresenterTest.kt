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
        assertThat(state.guidanceItems).contains("Use this account only for Spanish learning during training.")
        assertThat(state.secondaryAction).isEqualTo("Open TikTok")
        assertThat(state.primaryAction).isEqualTo("Mark step done")
    }

    @Test
    fun followCreatorsStepShowsRecommendedCreators() {
        val state = SpanishJourneyPresenter().present(
            SpanishTrainingJourney(completedSteps = setOf(SpanishTrainingStep.FreshAccount)),
        )

        assertThat(state.prompt).contains("Follow")
        assertThat(state.guidanceItems).containsAtLeast(
            "Spanish With Vicente",
            "Spanish After Hours",
            "Easy Spanish",
        )
    }

    @Test
    fun searchTermsStepShowsRecommendedSearches() {
        val state = SpanishJourneyPresenter().present(
            SpanishTrainingJourney(
                completedSteps = setOf(
                    SpanishTrainingStep.FreshAccount,
                    SpanishTrainingStep.FollowCreators,
                ),
            ),
        )

        assertThat(state.guidanceItems).containsAtLeast(
            "learn Spanish beginner",
            "Spanish A1 conversation",
            "Spanish listening practice",
        )
    }

    @Test
    fun excludeContentStepExplainsSkipAndNotInterestedBehavior() {
        val state = SpanishJourneyPresenter().present(
            SpanishTrainingJourney(
                completedSteps = setOf(
                    SpanishTrainingStep.FreshAccount,
                    SpanishTrainingStep.FollowCreators,
                    SpanishTrainingStep.SearchTerms,
                ),
            ),
        )

        assertThat(state.guidanceItems.joinToString()).contains("Skip")
        assertThat(state.guidanceItems.joinToString()).contains("Not interested")
    }

    @Test
    fun initialBatchStepExplainsGreenAndRedFrameBehavior() {
        val state = SpanishJourneyPresenter().present(
            SpanishTrainingJourney(
                completedSteps = setOf(
                    SpanishTrainingStep.FreshAccount,
                    SpanishTrainingStep.FollowCreators,
                    SpanishTrainingStep.SearchTerms,
                    SpanishTrainingStep.ExcludeContent,
                ),
            ),
        )

        assertThat(state.guidanceItems.joinToString()).contains("Green")
        assertThat(state.guidanceItems.joinToString()).contains("Red")
        assertThat(state.guidanceItems.joinToString()).contains("10 videos")
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
