package fyi.manpreet.brightstart.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.Boolean

data class Alarm(
    val id: Long = 0L,
    val localTime: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val time: String,
    val name: String,
    val ringtoneReference: String,
    val ringtoneName: String,
    val volume: Int, // TODO Add int range
    val vibrationStatus: Boolean,
    val alarmDays: List<AlarmDays>,
    val isActive: Boolean,
) {

    data class AlarmDays(
        val id: DaysEnum,
        val day: String,
        val isSelected: Boolean,
    )
}

enum class DaysEnum {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
