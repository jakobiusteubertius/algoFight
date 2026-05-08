package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TikTokCropPlannerTest {
    @Test
    fun createsCaptionCreatorAndSubtitleRegionsInsideScreenBounds() {
        val regions = TikTokCropPlanner().regionsFor(width = 1080, height = 2400)

        assertThat(regions.map { it.name }).containsExactly("creator", "caption", "subtitle")
        regions.forEach { region ->
            assertThat(
                region.left >= 0 &&
                region.top >= 0 &&
                region.right <= 1080 &&
                region.bottom <= 2400 &&
                region.width() > 0 &&
                region.height() > 0,
            ).isTrue()
        }
    }
}
