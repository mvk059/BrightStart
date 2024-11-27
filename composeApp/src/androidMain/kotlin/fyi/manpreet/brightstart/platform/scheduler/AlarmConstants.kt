package fyi.manpreet.brightstart.platform.scheduler

import android.app.PendingIntent

object AlarmConstants {
    const val CHANNEL_NAME = "Alarms"
    const val ALARM_ID = "alarm_id"
    const val ALARM_NAME = "alarm_name"
    const val RINGTONE_REF = "ringtone_ref"
    const val RINGTONE_NAME = "ringtone_name"
    const val UNIQUE_ID = "unique_id"
    const val FULL_SCREEN = "full_screen"
    const val ALARM_ACTION = "fyi.manpreet.brightstart.ALARM_TRIGGER"
    const val SNOOZE_ACTION = "fyi.manpreet.brightstart.ACTION_SNOOZE"
    const val CLOSE_ACTION = "fyi.manpreet.brightstart.ACTION_CLOSE"
    const val DISMISS_ACTION = "fyi.manpreet.brightstart.ACTION_DISMISS"
    const val PENDING_INTENT_FLAGS =
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
}
