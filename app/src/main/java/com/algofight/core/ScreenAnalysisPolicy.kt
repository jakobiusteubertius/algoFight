package com.algofight.core

class ScreenAnalysisPolicy {
    fun evaluate(
        hasMediaProjectionConsent: Boolean,
        sessionActive: Boolean,
        foregroundPackage: String?,
    ): ScreenAnalysisDecision {
        if (!hasMediaProjectionConsent) {
            return ScreenAnalysisDecision(allowed = false, reason = ScreenAnalysisBlockedReason.NoConsent)
        }
        if (!sessionActive) {
            return ScreenAnalysisDecision(allowed = false, reason = ScreenAnalysisBlockedReason.SessionInactive)
        }
        if (foregroundPackage !in OverlayActivationPolicy.tikTokPackages) {
            return ScreenAnalysisDecision(allowed = false, reason = ScreenAnalysisBlockedReason.NotTikTok)
        }
        return ScreenAnalysisDecision(allowed = true)
    }
}
