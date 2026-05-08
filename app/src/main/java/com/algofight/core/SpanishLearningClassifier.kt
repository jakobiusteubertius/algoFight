package com.algofight.core

class SpanishLearningClassifier(
    private val knownCreators: Set<String> = defaultKnownCreators,
) {
    fun classify(ocrText: String): ClassificationResult {
        val normalized = normalize(ocrText)
        if (normalized.isBlank()) {
            return ClassificationResult(FrameColor.Grey, setOf(ClassificationReason.EmptyOcrText))
        }

        val wordCount = normalized.split(Regex("\\s+")).filter { it.isNotBlank() }.size
        if (wordCount < MIN_READABLE_WORDS) {
            return ClassificationResult(FrameColor.Grey, setOf(ClassificationReason.LowReadableText))
        }

        val reasons = linkedSetOf<ClassificationReason>()
        if (spanishLearningKeywords.any { normalized.contains(it) }) {
            reasons += ClassificationReason.SpanishLearningKeyword
        }
        if (spanishWords.any { normalized.containsWord(it) }) {
            reasons += ClassificationReason.SpanishWord
        }
        if (knownCreators.any { normalized.contains(normalize(it)) }) {
            reasons += ClassificationReason.KnownCreator
        }

        if (reasons.isNotEmpty()) {
            return ClassificationResult(FrameColor.Green, reasons)
        }

        if (offGoalKeywords.any { normalized.containsWord(it) }) {
            return ClassificationResult(FrameColor.Red, setOf(ClassificationReason.OffGoalKeyword))
        }

        return ClassificationResult(
            FrameColor.Red,
            setOf(ClassificationReason.EnoughReadableTextWithoutLearningSignal),
        )
    }

    private fun normalize(value: String): String =
        value.lowercase()
            .replace('#', ' ')
            .replace('_', ' ')
            .replace(Regex("[^\\p{L}\\p{Nd}\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()

    private fun String.containsWord(word: String): Boolean =
        Regex("(^|\\s)${Regex.escape(word)}($|\\s)").containsMatchIn(this)

    private companion object {
        const val MIN_READABLE_WORDS = 3

        val defaultKnownCreators = setOf(
            "spanish with vicente",
            "butterfly spanish",
            "senor jordan",
            "the spanish dude",
        )

        val spanishLearningKeywords = setOf(
            "learn spanish",
            "spanish lesson",
            "spanish beginner",
            "spanish grammar",
            "spanish vocabulary",
            "spanish phrases",
            "espanol para principiantes",
            "aprender espanol",
            "clase de espanol",
        )

        val spanishWords = setOf(
            "hola",
            "gracias",
            "por",
            "favor",
            "buenos",
            "dias",
            "como",
            "estas",
            "quiero",
            "necesito",
            "porque",
            "cuando",
            "donde",
            "hablar",
            "espanol",
        )

        val offGoalKeywords = setOf(
            "gaming",
            "fortnite",
            "makeup",
            "celebrity",
            "gossip",
            "prank",
            "crypto",
            "trading",
            "football",
            "nba",
            "meme",
        )
    }
}
