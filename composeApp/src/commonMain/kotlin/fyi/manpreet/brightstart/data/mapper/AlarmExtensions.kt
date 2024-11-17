package fyi.manpreet.brightstart.data.mapper

import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.data.model.DaysEnum
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<AlarmDays>.constructRepeatDays(currentTime: LocalDateTime): String {
    val selectedDays = this.filter { it.isSelected.value }.map { it.id }
    val allDays = DaysEnum.entries

    return when {
        selectedDays.isEmpty() -> {
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            if (currentTime.hour < today.hour) "Tomorrow" else "Today"
        }

        selectedDays.containsAll(allDays.subList(0, 5)) && selectedDays.size == 5 -> "Weekdays"
        selectedDays.containsAll(allDays.subList(5, 7)) && selectedDays.size == 2 -> "Weekends"
        selectedDays.size == 7 -> "Everyday"
        else -> this.filter { it.isSelected.value }.joinToString { it.day.value }
    }

}

fun Int.getSelectedHourIndex(): Int {
    // TODO Handle for 24 hours
    val startRange = ((10 * 12) / 2) //- 1
    val endRange = (((12 * 10) / 2) + 12) - 1
    val range = startRange..endRange // Get the middle row of hours
    val value = if (this > 12) this % 12 else this
    val searchValue = value + startRange - 1
    range.forEach { index ->
        if (searchValue == index) {
            return index
        }
    }
    return -1
}

fun Int.getSelectedMinuteIndex(): Int {
    val startRange = ((10 * 60) / 2) //- 1
    val endRange = (((10 * 60) / 2) + 59) //- 1
    val range = startRange..endRange // Get the middle row of minutes
    range.forEach { index ->
        if ((this /*% 60*/) == (index % 60)) {
            return index
        }
    }
    return -1
}