package com.algofight

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.algofight.core.TrainingReminderPlanner
import java.util.Calendar

class TrainingReminderScheduler(private val context: Context) {
    fun scheduleDaily() {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val now = System.currentTimeMillis()
        val triggerAt = TrainingReminderPlanner().nextTriggerAt(
            nowEpochMillis = now,
            todayPreferredEpochMillis = todayPreferredReminderTime(now),
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAt,
            TrainingReminderPlanner.DAY_MILLIS,
            reminderIntent(),
        )
    }

    private fun todayPreferredReminderTime(now: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = now
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun reminderIntent(): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, TrainingReminderReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
}
