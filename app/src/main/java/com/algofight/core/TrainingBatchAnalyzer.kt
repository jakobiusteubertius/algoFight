package com.algofight.core

class TrainingBatchAnalyzer {
    fun analyze(decisions: List<FrameColor>, feedback: BatchFeedback): BatchAnalysis {
        val greenCount = decisions.count { it == FrameColor.Green }
        val redCount = decisions.count { it == FrameColor.Red }
        val greyCount = decisions.count { it == FrameColor.Grey }

        return BatchAnalysis(
            greenCount = greenCount,
            redCount = redCount,
            greyCount = greyCount,
            feedback = feedback,
            modelAgreement = agreementFor(greenCount, redCount, feedback),
        )
    }

    private fun agreementFor(
        greenCount: Int,
        redCount: Int,
        feedback: BatchFeedback,
    ): ModelAgreement =
        when (feedback) {
            BatchFeedback.MostlySpanish ->
                if (greenCount > redCount) ModelAgreement.Agree else ModelAgreement.Disagree
            BatchFeedback.MostlyJunk ->
                if (redCount > greenCount) ModelAgreement.Agree else ModelAgreement.Disagree
            BatchFeedback.Mixed -> ModelAgreement.LowConfidence
        }
}
