package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SpanishLearningClassifierTest {
    private val classifier = SpanishLearningClassifier(
        knownCreators = setOf("Spanish With Vicente"),
    )

    @Test
    fun classifiesSpanishLearningHashtagsAsGreen() {
        val result = classifier.classify("#learnspanish Spanish beginner phrases for travel")

        assertThat(result.color).isEqualTo(FrameColor.Green)
        assertThat(result.reasons).contains(ClassificationReason.SpanishLearningKeyword)
    }

    @Test
    fun classifiesKnownCreatorAsGreen() {
        val result = classifier.classify("Spanish With Vicente basic greetings lesson")

        assertThat(result.color).isEqualTo(FrameColor.Green)
        assertThat(result.reasons).contains(ClassificationReason.KnownCreator)
    }

    @Test
    fun classifiesEmptyTextAsGrey() {
        val result = classifier.classify("")

        assertThat(result.color).isEqualTo(FrameColor.Grey)
        assertThat(result.reasons).containsExactly(ClassificationReason.EmptyOcrText)
    }

    @Test
    fun classifiesLowSignalTextAsGrey() {
        val result = classifier.classify("wow")

        assertThat(result.color).isEqualTo(FrameColor.Grey)
        assertThat(result.reasons).containsExactly(ClassificationReason.LowReadableText)
    }

    @Test
    fun classifiesReadableOffGoalTextAsRed() {
        val result = classifier.classify("funny football meme compilation tonight")

        assertThat(result.color).isEqualTo(FrameColor.Red)
        assertThat(result.reasons).contains(ClassificationReason.OffGoalKeyword)
    }
}
