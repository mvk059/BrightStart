package fyi.manpreet.brightstart.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fyi.manpreet.brightstart.data.model.Alarm
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

actual class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    actual override fun schedule(alarm: Alarm) {
        val time = alarm.localTime

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarm.id)
            putExtra("ALARM_NAME", alarm.name.value)
            putExtra("RINGTONE_REF", alarm.ringtoneReference.value)
            putExtra("RINGTONE_NAME", alarm.ringtoneName.value)
            action = "fyi.manpreet.brightstart.ALARM_TRIGGER"
        }
        println("Ringtone Ref impl: , ${alarm.ringtoneReference.value}")

//        val intent =
//            Intent(context, MainActivity::class.java) // Replace with your app's main activity
//                .apply {
//                    putExtra("ALARM_ID", alarm.id)
//                    putExtra("ALARM_NAME", alarm.name.value)
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id.toInt(),   // If same object is passed, alarm is updated. Send ID in object TODO
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time.toInstant(TimeZone.currentSystemDefault()).epochSeconds * 1000, // System.currentTimeMillis() + 10 * 1000,
            pendingIntent
        )


    }

    actual override fun cancel(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


}