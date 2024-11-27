package fyi.manpreet.brightstart.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import fyi.manpreet.brightstart.ui.model.TimePeriod
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.Boolean
import kotlin.jvm.JvmInline

data class Alarm(
    val id: Long = INVALID_ID,
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
    val repeatDays: String,
    val isActive: AlarmActive,
    val timeLeftForAlarm: String,
    val icon: ImageVector,
) {

    data class AlarmDays(
        val id: DaysEnum,
        val day: AlarmDayTitle,
        val isSelected: AlarmDaySelected,
    )

    companion object {
        const val INVALID_ID = 0L
    }
}

data class AlarmTime(val value: String, val hour: Hour, val minute: Minute)

@JvmInline
value class AlarmName(val value: String)

@JvmInline
value class RingtoneReference(val value: String)

@JvmInline
value class RingtoneName(val value: String)

@JvmInline
value class Volume(val value: Int)

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
