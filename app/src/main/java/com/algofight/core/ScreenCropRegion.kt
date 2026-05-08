package com.algofight.core

data class ScreenCropRegion(
    val name: String,
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
) {
    fun width(): Int = right - left

    fun height(): Int = bottom - top
}
