package com.algofight.core

data class PendingBatchFeedback(
    val decisions: List<FrameColor>,
) {
    val summary: String = "${decisions.size} videos ready for feedback"
}
