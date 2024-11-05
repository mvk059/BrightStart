package fyi.manpreet.brightstart.data.model

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