package fyi.manpreet.brightstart.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.Boolean

// TODO Use LocalDateTime instead of time?
data class Alarm(
    val localTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val time: String,
    val name: String,
    val ringtoneReference: String,
    val ringtoneName: String,
    val volume: Int, // TODO Add int range
    val vibrationStatus: Boolean,
    val alarmDays: AlarmDays,
    val isActive: Boolean,
)

// TODO Replace this with AlarmDays???
data class AlarmDays(
    val monday: Boolean = false,
    val tuesday: Boolean = false,
    val wednesday: Boolean = false,
    val thursday: Boolean = false,
    val friday: Boolean = false,
    val saturday: Boolean = false,
    val sunday: Boolean = false,
)
