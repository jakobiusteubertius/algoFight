package com.algofight.core

data class OverlayActivationResult(
    val visible: Boolean,
    val reason: OverlayHiddenReason? = null,
)
