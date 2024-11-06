package fyi.manpreet.brightstart.data.model

import kotlin.Boolean

data class Alarm(
    val time: String,
    val name: String,
    val ringtoneReference: String,
    val vibrationStatus: Boolean,
    val alarmDays: AlarmDays,
    val isActive: Boolean,
)

data class AlarmDays(
    val monday: Boolean = false,
    val tuesday: Boolean = false,
    val wednesday: Boolean = false,
    val thursday: Boolean = false,
    val friday: Boolean = false,
    val saturday: Boolean = false,
    val sunday: Boolean = false,
)

data class AlarmDaysItem(
    val id: DaysEnum,
    val day: String,
    val isSelected: Boolean,
)

enum class DaysEnum {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
