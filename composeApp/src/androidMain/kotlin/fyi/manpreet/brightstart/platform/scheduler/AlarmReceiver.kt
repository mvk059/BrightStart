package fyi.manpreet.brightstart.platform.scheduler

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import fyi.manpreet.brightstart.AlarmTriggerActivity
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.ALARM_ID
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.CHANNEL_NAME
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.CLOSE_ACTION
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.DISMISS_ACTION
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.FULL_SCREEN
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.PENDING_INTENT_FLAGS
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants.SNOOZE_ACTION
import org.koin.core.component.KoinComponent

// TODO RECEIVE_BOOT_COMPLETED permission
class AlarmReceiver() : BroadcastReceiver(), KoinComponent {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Callback when alarm is triggered
        val alarmId = intent?.getLongExtra("ALARM_ID", 0L) ?: return
        val alarmName = intent?.getStringExtra("ALARM_NAME") ?: return
        val ringtoneReference = intent?.getStringExtra("RINGTONE_REF") ?: return
        println("Alarm triggered: $alarmId, $alarmName")

        // Create the notification channel (required for Android 8.0 and above)
        val notificationManager = context?.getSystemService(NotificationManager::class.java)!!

        // Delete notification channels before creating a new one to avoid multiple channels in settings
        notificationManager.notificationChannels.forEach {
            notificationManager.deleteNotificationChannel(it.id)
        }

        val channel = NotificationChannel(
            ringtoneReference,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "BrightStartChannel channel description"
            setSound(Uri.parse(ringtoneReference), null)
        }
        notificationManager.createNotificationChannel(channel)

        val fullScreenIntent = Intent(context, AlarmTriggerActivity::class.java).apply {
            putExtra(FULL_SCREEN, true)
            putExtra(ALARM_ID, alarmId.toInt())
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val fullScreenPendingIntent =
            PendingIntent.getActivity(context, 0, fullScreenIntent, PENDING_INTENT_FLAGS)

        val snoozeIntent = Intent(context, AlarmNotificationReceiver::class.java).apply {
            action = SNOOZE_ACTION
            putExtra(ALARM_ID, alarmId.toInt())
        }
        val snoozePendingIntent =
            PendingIntent.getBroadcast(context, 0, snoozeIntent, PENDING_INTENT_FLAGS)

        val closeIntent = Intent(context, AlarmNotificationReceiver::class.java).apply {
            action = CLOSE_ACTION
            putExtra(ALARM_ID, alarmId.toInt())
        }
        val closePendingIntent =
            PendingIntent.getBroadcast(context, 0, closeIntent, PENDING_INTENT_FLAGS)

        val dismissIntent = Intent(context, AlarmNotificationReceiver::class.java).apply {
            action = DISMISS_ACTION
            putExtra(ALARM_ID, alarmId.toInt())
        }
        val dismissPendingIntent =
            PendingIntent.getBroadcast(context, 0, dismissIntent, PENDING_INTENT_FLAGS)

        // Build the notification
        val notification = NotificationCompat.Builder(context, ringtoneReference)
            .setSmallIcon(android.R.drawable.ic_menu_add)
            .setContentTitle(alarmName)
//            .setContentText("Time to wake up!")
//            .setSound(Uri.parse("content://media/external_primary/audio/media/1000000020"))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .addAction(-1, "Snooze", snoozePendingIntent) // TODO Get from strings
            .addAction(-1, "Close", closePendingIntent)
            .setDeleteIntent(dismissPendingIntent)
            .build()
        notification.flags = notification.flags or Notification.FLAG_INSISTENT

        // Show the notification
        notificationManager.notify(alarmId.toInt(), notification)
        // Pass the id to the broadcast and cancel it there
    }
}
