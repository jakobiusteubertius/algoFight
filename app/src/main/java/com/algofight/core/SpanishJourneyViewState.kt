package com.algofight.core

data class SpanishJourneyViewState(
    val title: String,
    val stepLabel: String,
    val prompt: String,
    val guidanceItems: List<String>,
    val primaryAction: String,
    val secondaryAction: String?,
)
