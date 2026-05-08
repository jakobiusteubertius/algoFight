package com.algofight

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TrainingReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.training_reminder_channel),
                NotificationManager.IMPORTANCE_DEFAULT,
            ),
        )
        manager.notify(NOTIFICATION_ID, notification(context))
    }

    private fun notification(context: Context): Notification {
        val launchIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setContentTitle(context.getString(R.string.training_reminder_title))
            .setContentText(context.getString(R.string.training_reminder_text))
            .setContentIntent(launchIntent)
            .setAutoCancel(true)
            .build()
    }

    private companion object {
        const val CHANNEL_ID = "training-reminders"
        const val NOTIFICATION_ID = 1003
    }
}
