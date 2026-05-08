package com.algofight.core

enum class ClassificationReason {
    EmptyOcrText,
    LowReadableText,
    SpanishLearningKeyword,
    SpanishWord,
    KnownCreator,
    OffGoalKeyword,
    EnoughReadableTextWithoutLearningSignal,
}
