package com.algofight

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context

class ForegroundPackageReader(private val context: Context) {
    fun currentPackage(now: Long = System.currentTimeMillis()): String? {
        val usageStats = context.getSystemService(UsageStatsManager::class.java)
        val events = usageStats.queryEvents(now - FOREGROUND_LOOKBACK_MS, now)
        val event = UsageEvents.Event()
        var latestPackage: String? = null
        while (events.hasNextEvent()) {
            events.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                latestPackage = event.packageName
            }
        }
        return latestPackage
    }

    private companion object {
        const val FOREGROUND_LOOKBACK_MS = 10_000L
    }
}
