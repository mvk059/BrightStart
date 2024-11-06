package fyi.manpreet.brightstart.ui.model

import fyi.manpreet.brightstart.data.model.Alarm

data class AlarmItem(
    val alarm: Alarm,
    val alarmDaysItem: AlarmDaysItem,
)

data class AlarmDaysItem(
    val id: DaysEnum,
    val day: String,
    val isSelected: Boolean,
)

enum class DaysEnum {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
