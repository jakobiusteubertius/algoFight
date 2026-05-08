package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ScreenAnalysisPolicyTest {
    private val policy = ScreenAnalysisPolicy()

    @Test
    fun allowsAnalysisOnlyWithConsentActiveSessionAndTikTokForeground() {
        val result = policy.evaluate(
            hasMediaProjectionConsent = true,
            sessionActive = true,
            foregroundPackage = "com.zhiliaoapp.musically",
        )

        assertThat(result.allowed).isTrue()
    }

    @Test
    fun blocksAnalysisWithoutConsent() {
        val result = policy.evaluate(
            hasMediaProjectionConsent = false,
            sessionActive = true,
            foregroundPackage = "com.zhiliaoapp.musically",
        )

        assertThat(result.allowed).isFalse()
        assertThat(result.reason).isEqualTo(ScreenAnalysisBlockedReason.NoConsent)
    }

    @Test
    fun blocksAnalysisOutsideTikTok() {
        val result = policy.evaluate(
            hasMediaProjectionConsent = true,
            sessionActive = true,
            foregroundPackage = "com.algofight",
        )

        assertThat(result.allowed).isFalse()
        assertThat(result.reason).isEqualTo(ScreenAnalysisBlockedReason.NotTikTok)
    }
}
