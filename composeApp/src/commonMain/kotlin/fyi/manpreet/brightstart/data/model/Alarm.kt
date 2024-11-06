package fyi.manpreet.brightstart.data.model

import kotlin.Boolean

data class Alarm(
    val time: String,
    val name: String,
    val ringtoneReference: String,
    val ringtoneName: String,
    val volume: Int, // TODO Add int range
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
