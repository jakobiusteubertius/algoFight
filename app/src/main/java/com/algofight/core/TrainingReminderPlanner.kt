package com.algofight.core

class TrainingReminderPlanner {
    fun nextTriggerAt(
        nowEpochMillis: Long,
        todayPreferredEpochMillis: Long,
    ): Long =
        if (todayPreferredEpochMillis > nowEpochMillis) {
            todayPreferredEpochMillis
        } else {
            todayPreferredEpochMillis + DAY_MILLIS
        }

    companion object {
        const val DAY_MILLIS = 24 * 60 * 60 * 1000L
    }
}
