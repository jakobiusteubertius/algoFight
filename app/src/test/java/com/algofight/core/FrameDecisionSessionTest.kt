package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FrameDecisionSessionTest {
    @Test
    fun createsPendingFeedbackAfterFiveFrameDecisions() {
        val session = FrameDecisionSession()
        val updated = repeatDecision(session, FrameColor.Green, 5)

        assertThat(updated.pendingFeedback?.decisions).hasSize(5)
        assertThat(updated.pendingFeedback?.summary).isEqualTo("5 videos ready for feedback")
    }

    @Test
    fun doesNotCreateFeedbackBeforeFiveDecisions() {
        val session = repeatDecision(FrameDecisionSession(), FrameColor.Green, 4)

        assertThat(session.pendingFeedback).isNull()
    }

    @Test
    fun recordsFeedbackAnalysisAndClearsPendingBatch() {
        val session = repeatDecision(FrameDecisionSession(), FrameColor.Green, 5)

        val updated = session.recordFeedback(BatchFeedback.MostlySpanish)

        assertThat(updated.pendingFeedback).isNull()
        assertThat(updated.completedBatches).hasSize(1)
        assertThat(updated.completedBatches.first().modelAgreement).isEqualTo(ModelAgreement.Agree)
    }

    private fun repeatDecision(
        session: FrameDecisionSession,
        decision: FrameColor,
        count: Int,
    ): FrameDecisionSession =
        (1..count).fold(session) { current, _ -> current.recordDecision(decision) }
}
