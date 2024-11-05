package fyi.manpreet.brightstart.data.mapper

import fyi.manpreet.brightstart.data.database.AlarmDaysTable
import fyi.manpreet.brightstart.data.database.AlarmTable
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays

fun Alarm.toAlarmTable(): AlarmTable {
    return AlarmTable(
        time = time,
        name = name,
        ringtoneReference = ringtoneReference,
        vibrationStatus = vibrationStatus,
        isActive = isActive,
    )
}

fun AlarmTable.toAlarm(alarmDays: AlarmDays = AlarmDays()): Alarm {
    return Alarm(
        time = time,
        name = name,
        ringtoneReference = ringtoneReference,
        vibrationStatus = vibrationStatus,
        alarmDays = alarmDays,
        isActive = isActive,
    )
}

// TODO Remove
//fun List<AlarmTable>.toAlarm(): List<Alarm> {
//    return map { it.toAlarm(alarmDays) }
//}

fun AlarmDays.toAlarmDaysTable(alarmId: Long): AlarmDaysTable {
    return AlarmDaysTable(
        alarmId = alarmId,
        monday = monday,
        tuesday = tuesday,
        wednesday = wednesday,
        thursday = thursday,
        friday = friday,
        saturday = saturday,
        sunday = sunday,
    )
}

fun AlarmDaysTable.toAlarmDays(): AlarmDays {
    return AlarmDays(
        monday = monday,
        tuesday = tuesday,
        wednesday = wednesday,
        thursday = thursday,
        friday = friday,
        saturday = saturday,
        sunday = sunday,
    )
}
