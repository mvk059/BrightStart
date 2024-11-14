package fyi.manpreet.brightstart.scheduler

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import fyi.manpreet.brightstart.scheduler.AlarmReceiver.Companion.ALARM_ID
import fyi.manpreet.brightstart.scheduler.AlarmReceiver.Companion.CLOSE_ACTION
import fyi.manpreet.brightstart.scheduler.AlarmReceiver.Companion.DISMISS_ACTION
import fyi.manpreet.brightstart.scheduler.AlarmReceiver.Companion.SNOOZE_ACTION

class AlarmNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(NotificationManager::class.java)!!
        val snooze = intent?.getBooleanExtra("SNOOZE", false)
        val close = intent?.getBooleanExtra("CLOSE", false)
        val dismiss = intent?.getBooleanExtra("DISMISS", false)
        val empty = intent?.getBooleanExtra("EMPTY", false)
        val id = intent?.getIntExtra(ALARM_ID, -1) ?: -1

        when (intent?.action) {
            SNOOZE_ACTION -> {
                // Handle snooze action
                Log.d("NotificationAction", "Snooze clicked")
            }

            CLOSE_ACTION -> {
                // Handle close action
                Log.d("NotificationAction", "Close clicked")
            }

            DISMISS_ACTION -> {
                // Handle notification dismiss
                Log.d("NotificationAction", "Notification dismissed")
            }
        }

        Log.d(
            "NotificationAction",
            """
                Snooze: $snooze
                Close: $close
                Dismiss: $dismiss
                Empty: $empty
                ID: $id
                """.trimIndent()
        )

        notificationManager.cancel(id)
    }

    companion object {

    }
}