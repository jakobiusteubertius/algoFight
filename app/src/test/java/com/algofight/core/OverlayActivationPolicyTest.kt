package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class OverlayActivationPolicyTest {
    private val policy = OverlayActivationPolicy()

    @Test
    fun showsOverlayWhenSessionIsActiveAndTikTokIsForeground() {
        val result = policy.evaluate(
            sessionActive = true,
            foregroundPackage = "com.zhiliaoapp.musically",
        )

        assertThat(result.visible).isTrue()
    }

    @Test
    fun hidesOverlayWhenSessionIsInactive() {
        val result = policy.evaluate(
            sessionActive = false,
            foregroundPackage = "com.zhiliaoapp.musically",
        )

        assertThat(result.visible).isFalse()
        assertThat(result.reason).isEqualTo(OverlayHiddenReason.SessionInactive)
    }

    @Test
    fun hidesOverlayOutsideTikTok() {
        val result = policy.evaluate(
            sessionActive = true,
            foregroundPackage = "com.algofight",
        )

        assertThat(result.visible).isFalse()
        assertThat(result.reason).isEqualTo(OverlayHiddenReason.NotTikTok)
    }

    @Test
    fun recognizesInternationalTikTokPackage() {
        val result = policy.evaluate(
            sessionActive = true,
            foregroundPackage = "com.ss.android.ugc.trill",
        )

        assertThat(result.visible).isTrue()
    }
}
