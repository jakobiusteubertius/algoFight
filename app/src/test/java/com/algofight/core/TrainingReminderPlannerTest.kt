package com.algofight.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TrainingReminderPlannerTest {
    @Test
    fun schedulesTodayWhenPreferredTimeIsStillAhead() {
        val trigger = TrainingReminderPlanner().nextTriggerAt(
            nowEpochMillis = 10_000L,
            todayPreferredEpochMillis = 20_000L,
        )

        assertThat(trigger).isEqualTo(20_000L)
    }

    @Test
    fun schedulesTomorrowWhenPreferredTimeAlreadyPassed() {
        val trigger = TrainingReminderPlanner().nextTriggerAt(
            nowEpochMillis = 30_000L,
            todayPreferredEpochMillis = 20_000L,
        )

        assertThat(trigger).isEqualTo(20_000L + TrainingReminderPlanner.DAY_MILLIS)
    }
}
