package fyi.manpreet.brightstart.scheduler

import android.app.PendingIntent

object AlarmConstants {
    const val CHANNEL_NAME = "Alarms"
    const val ALARM_ID = "alarm_id"
    const val FULL_SCREEN = "full_screen"
    const val SNOOZE_ACTION = "fyi.manpreet.brightstart.ACTION_SNOOZE"
    const val CLOSE_ACTION = "fyi.manpreet.brightstart.ACTION_CLOSE"
    const val DISMISS_ACTION = "fyi.manpreet.brightstart.ACTION_DISMISS"
    const val PENDING_INTENT_FLAGS =
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
}
