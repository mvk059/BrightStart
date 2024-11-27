package fyi.manpreet.brightstart.platform.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fyi.manpreet.brightstart.data.mapper.calculateNextAlarmTime
import fyi.manpreet.brightstart.data.model.Alarm
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

actual class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    // TODO Handle snooze with repeat days
    actual override fun schedule(alarm: Alarm) {
        val idsTime = alarm.getIdsAndTime()
        println("calculateNext IDs: ${idsTime.joinToString()}")

        idsTime.forEach { (id, time) ->
            val pendingIntent = createPendingIntent(createIntent(alarm, id), id)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time.toInstant(TimeZone.currentSystemDefault()).epochSeconds * 1000, // System.currentTimeMillis() + 10 * 1000,
                pendingIntent
            )
        }
    }

    actual override fun cancel(alarm: Alarm) {
        println("Cancel Alarm: ${alarm.id}")
        val idsTime = alarm.getIdsAndTime()
        println("calculateNext cancel IDs: ${idsTime.joinToString()}")
        idsTime.forEach { (id, _) ->
            val pendingIntent = createPendingIntent(createIntent(alarm, id), id)
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun Alarm.getIdsAndTime(): List<Pair<Int, LocalDateTime>> {
        // Alarm is scheduled for today/tomorrow
        if (this.alarmDays.none { it.isSelected.value }) {
            println("calculateNextAlarmTime single: ${listOf(this.id.toInt() to this.localTime)}")
            return listOf(this.id.toInt() to this.localTime)
        }

        // Alarm is scheduled for specific days
        return this.alarmDays
            .filter { it.isSelected.value }
            .map { "${this.id}-${it.id.name}".hashCode() to this.localTime.calculateNextAlarmTime(it) }
    }

    private fun createIntent(alarm: Alarm, uniqueId: Int): Intent {
        return Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmConstants.ALARM_ID, alarm.id)
            putExtra(AlarmConstants.ALARM_NAME, alarm.name.value)
            putExtra(AlarmConstants.RINGTONE_REF, alarm.ringtoneReference.value)
            putExtra(AlarmConstants.RINGTONE_NAME, alarm.ringtoneName.value)
            putExtra(AlarmConstants.UNIQUE_ID, uniqueId)
            action = AlarmConstants.ALARM_ACTION
        }
    }

    private fun createPendingIntent(intent: Intent, uniqueId: Int): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            uniqueId,   // If same object is passed, alarm is updated. Send ID in object
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
