package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ProgressPresenterTest {
    @Test
    fun showsNoBatchesYetBeforeFeedback() {
        val state = ProgressPresenter().present(ProgressStats(completedBatches = 0))

        assertThat(state.title).isEqualTo("No feedback batches yet")
        assertThat(state.detail).contains("Run a TikTok training session")
    }

    @Test
    fun showsCompletedBatchCountAndLatestAgreement() {
        val state = ProgressPresenter().present(
            ProgressStats(
                completedBatches = 3,
                latestAgreement = ModelAgreement.Agree,
            ),
        )

        assertThat(state.title).isEqualTo("3 feedback batches")
        assertThat(state.detail).contains("latest model check agreed")
    }
}
