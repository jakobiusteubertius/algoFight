package com.algofight.core

class ProgressPresenter {
    fun present(stats: ProgressStats): ProgressViewState {
        if (stats.completedBatches == 0) {
            return ProgressViewState(
                title = "No feedback batches yet",
                detail = "Run a TikTok training session, then rate the first 5-video batch.",
            )
        }

        return ProgressViewState(
            title = "${stats.completedBatches} feedback batches",
            detail = when (stats.latestAgreement) {
                ModelAgreement.Agree -> "The latest model check agreed with your feedback."
                ModelAgreement.Disagree -> "The latest model check disagreed with your feedback."
                ModelAgreement.LowConfidence -> "The latest model check needs more feedback."
                null -> "Keep rating batches to improve the model check."
            },
        )
    }
}
