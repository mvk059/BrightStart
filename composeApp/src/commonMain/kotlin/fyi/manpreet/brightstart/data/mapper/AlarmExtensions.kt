package fyi.manpreet.brightstart.data.mapper

import androidx.compose.ui.graphics.vector.ImageVector
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.data.model.DaysEnum
import fyi.manpreet.brightstart.ui.home.items.icons.NightIcon
import fyi.manpreet.brightstart.ui.home.items.icons.SunFullIcon
import fyi.manpreet.brightstart.ui.home.items.icons.SunMediumIcon
import fyi.manpreet.brightstart.ui.home.items.icons.SunriseIcon
import fyi.manpreet.brightstart.ui.home.items.icons.SunsetIcon
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit

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

fun LocalDateTime.formatLocalDateTimeToHHMM(): String {
    val hour = this.hour
    val minute = this.minute
    val formattedHour = if (hour == 0 || hour == 12) 12 else hour % 12
    val period = if (hour < 12) "AM" else "PM"
    return buildString {
        append(formattedHour.toString().padStart(2, '0'))
        append(":")
        append(minute.toString().padStart(2, '0'))
    }
}

fun LocalDateTime.calculateNextAlarmTime(days: AlarmDays): LocalDateTime {
    // Alarm is scheduled for specific days
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val targetDayOfWeek = days.toDayOfWeek()

    // Extract hour and minute from alarm's localTime
    val alarmHour = this.hour
    val alarmMinute = this.minute

    // Extract hour and minute from current time
    val currentHour = currentDateTime.hour
    val currentMinute = currentDateTime.minute

    // Calculate next occurrence of the specific day within the week
    val nextAlarmDateTime =
        if (currentDateTime.dayOfWeek.ordinal <= targetDayOfWeek.ordinal && (currentHour < alarmHour || (currentHour == alarmHour && currentMinute < alarmMinute))) {
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

fun AlarmDays.toDayOfWeek(): DayOfWeek {
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

fun calculateTimeBetweenWithText(
    selectedDateTime: Instant,
    systemDateTime: Instant = Clock.System.now(),
    alarmDays: List<AlarmDays>,
    text: String = "Alarm in "
): String {
    val time = calculateTimeBetween(selectedDateTime, systemDateTime, alarmDays)
    return buildString {
        append(text)
        append(time)
    }.trim()
}

private fun calculateTimeBetween(
    selectedDateTime: Instant,
    systemDateTime: Instant = Clock.System.now(),
    alarmDays: List<AlarmDays>
): String {
    val selectedLocalDateTime =
        selectedDateTime.toLocalDateTime(TimeZone.currentSystemDefault())
    val selectedLocalDateTimeAlarmDays = alarmDays
        .filter { it.isSelected.value }
        .map { selectedLocalDateTime.calculateNextAlarmTime(it) }

    val firstSelectedLocalDateTimeAlarmDay: Instant? =
        selectedLocalDateTimeAlarmDays.sorted().firstOrNull()
            ?.toInstant(TimeZone.currentSystemDefault())

    val updatedSelectedDateTime = firstSelectedLocalDateTimeAlarmDay ?: selectedDateTime
    val duration = updatedSelectedDateTime.minus(systemDateTime)

    val days = duration.toInt(DurationUnit.DAYS)
    val hours = duration.toInt(DurationUnit.HOURS) % 24
    val minutes = duration.toInt(DurationUnit.MINUTES) % 60

    return buildString {
        if (days > 0) append("${days}d ")
        if (hours > 0 || days > 0) append("${hours}h ")
        append("${minutes}min")
    }.trim()
}

fun LocalDateTime.getIconForTime(): ImageVector {
    val hour = this.hour
    return when (hour) {
        in 0..4 -> NightIcon
        in 4..7 -> SunriseIcon
        in 8..12 -> SunMediumIcon
        in 13..16 -> SunFullIcon
        in 17..19 -> SunsetIcon
        in 20..23 -> NightIcon
        else -> SunFullIcon
    }
}