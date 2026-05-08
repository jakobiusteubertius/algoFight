package com.algofight.core

data class BatchAnalysis(
    val greenCount: Int,
    val redCount: Int,
    val greyCount: Int,
    val feedback: BatchFeedback,
    val modelAgreement: ModelAgreement,
)
