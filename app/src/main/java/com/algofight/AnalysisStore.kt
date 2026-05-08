package com.algofight

import android.content.Context
import com.algofight.core.BatchFeedback
import com.algofight.core.FrameColor
import com.algofight.core.FrameDecisionSession
import com.algofight.core.PendingBatchFeedback

class AnalysisStore(context: Context) {
    private val preferences = context.getSharedPreferences("analysis", Context.MODE_PRIVATE)

    fun latestFrameColor(): FrameColor =
        preferences.getString(KEY_LATEST_FRAME_COLOR, null)
            ?.let { runCatching { FrameColor.valueOf(it) }.getOrNull() }
            ?: FrameColor.Grey

    fun pendingFeedback(): PendingBatchFeedback? =
        preferences.getString(KEY_PENDING_DECISIONS, null)
            ?.takeIf { it.isNotBlank() }
            ?.let { encoded -> PendingBatchFeedback(decodeColors(encoded)) }

    fun recordDecision(color: FrameColor) {
        val session = loadSession().recordDecision(color)
        saveSession(session)
        preferences.edit()
            .putString(KEY_LATEST_FRAME_COLOR, color.name)
            .apply()
    }

    fun recordFeedback(feedback: BatchFeedback) {
        saveSession(loadSession().recordFeedback(feedback))
    }

    private fun loadSession(): FrameDecisionSession =
        FrameDecisionSession(
            activeDecisions = decodeColors(preferences.getString(KEY_ACTIVE_DECISIONS, null).orEmpty()),
            pendingFeedback = pendingFeedback(),
        )

    private fun saveSession(session: FrameDecisionSession) {
        preferences.edit()
            .putString(KEY_ACTIVE_DECISIONS, encodeColors(session.activeDecisions))
            .putString(KEY_PENDING_DECISIONS, encodeColors(session.pendingFeedback?.decisions.orEmpty()))
            .apply()
    }

    private fun encodeColors(colors: List<FrameColor>): String =
        colors.joinToString(separator = ",") { it.name }

    private fun decodeColors(encoded: String): List<FrameColor> =
        encoded.split(',')
            .mapNotNull { raw ->
                raw.trim()
                    .takeIf { it.isNotBlank() }
                    ?.let { runCatching { FrameColor.valueOf(it) }.getOrNull() }
            }

    private companion object {
        const val KEY_LATEST_FRAME_COLOR = "latest_frame_color"
        const val KEY_ACTIVE_DECISIONS = "active_decisions"
        const val KEY_PENDING_DECISIONS = "pending_decisions"
    }
}
