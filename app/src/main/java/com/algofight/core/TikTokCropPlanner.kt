package com.algofight.core

class TikTokCropPlanner {
    fun regionsFor(width: Int, height: Int): List<ScreenCropRegion> =
        listOf(
            region("creator", width, height, 0.04f, 0.68f, 0.76f, 0.76f),
            region("caption", width, height, 0.04f, 0.74f, 0.86f, 0.88f),
            region("subtitle", width, height, 0.10f, 0.42f, 0.90f, 0.62f),
        )

    private fun region(
        name: String,
        width: Int,
        height: Int,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
    ): ScreenCropRegion =
        ScreenCropRegion(
            name = name,
            left = (width * left).toInt().coerceIn(0, width),
            top = (height * top).toInt().coerceIn(0, height),
            right = (width * right).toInt().coerceIn(0, width),
            bottom = (height * bottom).toInt().coerceIn(0, height),
        )
}
