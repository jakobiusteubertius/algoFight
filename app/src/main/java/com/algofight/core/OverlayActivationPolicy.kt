package com.algofight.core

class OverlayActivationPolicy {
    fun evaluate(
        sessionActive: Boolean,
        foregroundPackage: String?,
    ): OverlayActivationResult {
        if (!sessionActive) {
            return OverlayActivationResult(visible = false, reason = OverlayHiddenReason.SessionInactive)
        }

        return if (foregroundPackage in tikTokPackages) {
            OverlayActivationResult(visible = true)
        } else {
            OverlayActivationResult(visible = false, reason = OverlayHiddenReason.NotTikTok)
        }
    }

    companion object {
        val tikTokPackages = setOf(
            "com.zhiliaoapp.musically",
            "com.ss.android.ugc.trill",
        )
    }
}
