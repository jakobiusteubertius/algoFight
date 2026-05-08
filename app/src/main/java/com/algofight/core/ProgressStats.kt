package com.algofight.core

data class ProgressStats(
    val completedBatches: Int,
    val latestAgreement: ModelAgreement? = null,
)
