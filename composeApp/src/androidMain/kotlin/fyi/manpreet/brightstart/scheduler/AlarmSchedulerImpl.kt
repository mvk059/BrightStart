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
            putExtra("ALARM_NAME", alarm.name.value)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time.toInstant(TimeZone.currentSystemDefault()).epochSeconds * 1000,
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),   // If same object is passed, alarm is updated. Send ID in object TODO
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
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