package fyi.manpreet.brightstart.data.model

import fyi.manpreet.brightstart.ui.model.TimePeriod
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.Boolean
import kotlin.jvm.JvmInline

// TODO Create value classes
data class Alarm(
    val id: Long = 0L,
    val localTime: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val time: AlarmTime,
    val name: AlarmName,
    val timePeriod: TimePeriod,
    val ringtoneReference: RingtoneReference,
    val ringtoneName: RingtoneName,
    val volume: Volume,
    val vibrationStatus: VibrationStatus,
    val alarmDays: List<AlarmDays>,
    val isActive: AlarmActive,
    val timeLeftForAlarm: String = "",
) {

    data class AlarmDays(
        val id: DaysEnum,
        val day: AlarmDayTitle,
        val isSelected: AlarmDaySelected,
    )
}

@JvmInline
value class AlarmTime(val value: String)

@JvmInline
value class AlarmName(val value: String)

@JvmInline
value class RingtoneReference(val value: String)

@JvmInline
value class RingtoneName(val value: String)

@JvmInline
value class Volume(/*@IntRange(0, 100)*/ val value: Int) // TODO Add int range

@JvmInline
value class VibrationStatus(val value: Boolean)

@JvmInline
value class AlarmActive(val value: Boolean)

@JvmInline
value class AlarmDayTitle(val value: String)

@JvmInline
value class AlarmDaySelected(val value: Boolean)

enum class DaysEnum {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
