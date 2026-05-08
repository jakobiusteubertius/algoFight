package com.algofight.core

data class ClassificationResult(
    val color: FrameColor,
    val reasons: Set<ClassificationReason>,
)
