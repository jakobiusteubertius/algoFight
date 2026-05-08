package com.algofight.core

data class FrameDecisionSession(
    val activeDecisions: List<FrameColor> = emptyList(),
    val pendingFeedback: PendingBatchFeedback? = null,
    val completedBatches: List<BatchAnalysis> = emptyList(),
) {
    fun recordDecision(decision: FrameColor): FrameDecisionSession {
        if (pendingFeedback != null) {
            return this
        }

        val updated = activeDecisions + decision
        return if (updated.size >= BATCH_SIZE) {
            copy(
                activeDecisions = emptyList(),
                pendingFeedback = PendingBatchFeedback(updated.take(BATCH_SIZE)),
            )
        } else {
            copy(activeDecisions = updated)
        }
    }

    fun recordFeedback(feedback: BatchFeedback): FrameDecisionSession {
        val pending = pendingFeedback ?: return this
        val analysis = TrainingBatchAnalyzer().analyze(pending.decisions, feedback)
        return copy(
            pendingFeedback = null,
            completedBatches = completedBatches + analysis,
        )
    }

    companion object {
        const val BATCH_SIZE = 5
    }
}
