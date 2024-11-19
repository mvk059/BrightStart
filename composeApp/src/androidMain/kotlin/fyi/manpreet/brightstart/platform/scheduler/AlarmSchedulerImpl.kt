package fyi.manpreet.brightstart.platform.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.DaysEnum
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

actual class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    actual override fun schedule(alarm: Alarm) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarm.id)
            putExtra("ALARM_NAME", alarm.name.value)
            putExtra("RINGTONE_REF", alarm.ringtoneReference.value)
            putExtra("RINGTONE_NAME", alarm.ringtoneName.value)
            action = "fyi.manpreet.brightstart.ALARM_TRIGGER"
        }
        println("Ringtone Ref impl: , ${alarm.ringtoneReference.value}")
        println("schedule Alarm: ${alarm.id}")

        val idsTime = alarm.getIdsAndTime()
        println("calculateNext IDs: ${idsTime.joinToString()}")
        idsTime.forEach { (id, time) ->
            val pendingIntent = createPendingIntent(intent, id)
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
            .map { "${this.id}-${it.id.name}".hashCode() to calculateNextAlarmTime(this, it) }
    }

    private fun calculateNextAlarmTime(alarm: Alarm, days: Alarm.AlarmDays): LocalDateTime {
        // Alarm is scheduled for specific days
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val targetDayOfWeek = days.toDayOfWeek()

        // Extract hour and minute from alarm's localTime
        val alarmHour = alarm.localTime.hour
        val alarmMinute = alarm.localTime.minute

        // Calculate next occurrence of the specific day within the week
        val nextAlarmDateTime = if (currentDateTime.dayOfWeek.ordinal <= targetDayOfWeek.ordinal) {
            // If target day is today or in the future this week
            val days = targetDayOfWeek.ordinal - currentDateTime.dayOfWeek.ordinal
            currentDateTime.date.plus(DatePeriod(days = days)).atTime(alarmHour, alarmMinute)
        } else {
            // If target day is in the next week
            val days = 7 - currentDateTime.dayOfWeek.ordinal + targetDayOfWeek.ordinal
            currentDateTime.date.plus(DatePeriod(days = days)).atTime(alarmHour, alarmMinute)
        }

        println("calculateNextAlarmTime Next: $nextAlarmDateTime")
        return nextAlarmDateTime
    }

    private fun createIntent(alarm: Alarm, uniqueId: Int): Intent {
        return Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarm.id)
            putExtra("ALARM_NAME", alarm.name.value)
            putExtra("RINGTONE_REF", alarm.ringtoneReference.value)
            putExtra("RINGTONE_NAME", alarm.ringtoneName.value)
            putExtra("UNIQUE_ID", uniqueId)
            action = "fyi.manpreet.brightstart.ALARM_TRIGGER"
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

    fun Alarm.AlarmDays.toDayOfWeek(): DayOfWeek {
        return when (this.id) {
            DaysEnum.MONDAY -> DayOfWeek.MONDAY
            DaysEnum.TUESDAY -> DayOfWeek.TUESDAY
            DaysEnum.WEDNESDAY -> DayOfWeek.WEDNESDAY
            DaysEnum.THURSDAY -> DayOfWeek.THURSDAY
            DaysEnum.FRIDAY -> DayOfWeek.FRIDAY
            DaysEnum.SATURDAY -> DayOfWeek.SATURDAY
            DaysEnum.SUNDAY -> DayOfWeek.SUNDAY
        }
    }

}