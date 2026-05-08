package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TrainingBatchAnalyzerTest {
    private val analyzer = TrainingBatchAnalyzer()

    @Test
    fun mostlySpanishFeedbackAgreesWithMostlyGreenDecisions() {
        val analysis = analyzer.analyze(
            decisions = listOf(
                FrameColor.Green,
                FrameColor.Green,
                FrameColor.Green,
                FrameColor.Red,
                FrameColor.Grey,
            ),
            feedback = BatchFeedback.MostlySpanish,
        )

        assertThat(analysis.greenCount).isEqualTo(3)
        assertThat(analysis.redCount).isEqualTo(1)
        assertThat(analysis.greyCount).isEqualTo(1)
        assertThat(analysis.modelAgreement).isEqualTo(ModelAgreement.Agree)
    }

    @Test
    fun mostlyJunkFeedbackAgreesWithMostlyRedDecisions() {
        val analysis = analyzer.analyze(
            decisions = listOf(
                FrameColor.Red,
                FrameColor.Red,
                FrameColor.Green,
                FrameColor.Grey,
                FrameColor.Red,
            ),
            feedback = BatchFeedback.MostlyJunk,
        )

        assertThat(analysis.modelAgreement).isEqualTo(ModelAgreement.Agree)
    }

    @Test
    fun mixedFeedbackIsLowConfidence() {
        val analysis = analyzer.analyze(
            decisions = listOf(
                FrameColor.Green,
                FrameColor.Red,
                FrameColor.Grey,
                FrameColor.Green,
                FrameColor.Red,
            ),
            feedback = BatchFeedback.Mixed,
        )

        assertThat(analysis.modelAgreement).isEqualTo(ModelAgreement.LowConfidence)
    }
}
