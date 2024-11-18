package fyi.manpreet.brightstart.platform.scheduler

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.koin.java.KoinJavaComponent.inject

class AlarmNotificationReceiver : BroadcastReceiver() {

    private val alarmInteraction: AlarmInteraction by inject(AlarmInteraction::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(NotificationManager::class.java)!!
        val snooze = intent?.getBooleanExtra("SNOOZE", false)
        val close = intent?.getBooleanExtra("CLOSE", false)
        val dismiss = intent?.getBooleanExtra("DISMISS", false)
        val empty = intent?.getBooleanExtra("EMPTY", false)
        val id = intent?.getIntExtra(AlarmConstants.ALARM_ID, -1) ?: -1

        when (intent?.action) {
            AlarmConstants.SNOOZE_ACTION -> {
                // Handle snooze action
                Log.d("NotificationAction", "Snooze clicked")
                notificationManager.cancel(id)
                alarmInteraction.onAlarmSnooze(id.toLong())
            }

            AlarmConstants.CLOSE_ACTION -> {
                // Handle close action
                Log.d("NotificationAction", "Close clicked")
                notificationManager.cancel(id)
                alarmInteraction.onAlarmDismiss(id.toLong())
            }

            AlarmConstants.DISMISS_ACTION -> {
                // Handle notification dismiss
                Log.d("NotificationAction", "Notification dismissed")
                notificationManager.cancel(id)
                alarmInteraction.onAlarmDismiss(id.toLong())
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
    }

    companion object {

    }
}