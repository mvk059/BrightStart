package fyi.manpreet.brightstart.scheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat

// TODO RECEIVE_BOOT_COMPLETED permission
class AlarmReceiver() : BroadcastReceiver() {

    // Inject things here
//    private val sharedViewModel: HomeViewModel by viewModel()
//    private val alarmTrigger: AlarmTrigger by inject(AlarmTrigger::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        // Callback when alarm is triggered
        val alarmId = intent?.getLongExtra("ALARM_ID", 0L) ?: return
        val alarmName = intent?.getStringExtra("ALARM_NAME") ?: return
        val ringtoneReference = intent?.getStringExtra("RINGTONE_REF") ?: return
        val ringtoneName = intent?.getStringExtra("RINGTONE_NAME") ?: return
        println("Alarm triggered: $alarmId, $alarmName")

//        context?.let {
//            println("Inside onReceive Context: $alarmId, $alarmName")
//            // Create an Intent to launch your app's AlarmTriggerActivity (or the desired activity)
//            val launchIntent = Intent(it, AlarmTriggerActivity::class.java).apply {
//                putExtra("ALARM_ID", alarmId)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            }
//            // Start the activity
//            it.startActivity(launchIntent)
//        }

        // Create the notification channel (required for Android 8.0 and above)
        val notificationManager = context?.getSystemService(NotificationManager::class.java)!!
        createNotificationChannel(notificationManager)

//        val alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.your_alarm_sound);
//        val  ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        val ringtoneUri = Uri.parse(ringtoneReference.substringBefore("?"))
        println("Ringtone Ref: , $ringtoneUri")


        // Build the notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_add)
            .setContentTitle(alarmName)
            .setContentText("Time to wake up!")
            .setSound(ringtoneUri)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        // Show the notification
        notificationManager.notify(alarmId.toInt(), notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

    }

    companion object {
        private const val CHANNEL_ID = "alarm_channel"
    }
}
