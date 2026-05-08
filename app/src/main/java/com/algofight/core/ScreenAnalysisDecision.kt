package com.algofight.core

data class ScreenAnalysisDecision(
    val allowed: Boolean,
    val reason: ScreenAnalysisBlockedReason? = null,
)
