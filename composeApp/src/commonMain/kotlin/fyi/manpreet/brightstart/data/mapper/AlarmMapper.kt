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
        ringtoneName = ringtoneName,
        volume = volume,
        vibrationStatus = vibrationStatus,
        isActive = isActive,
    )
}

fun AlarmTable.toAlarm(alarmDays: AlarmDays = AlarmDays()): Alarm {
    return Alarm(
        time = time,
        name = name,
        ringtoneReference = ringtoneReference,
        ringtoneName = ringtoneName,
        volume = volume,
        vibrationStatus = vibrationStatus,
        alarmDays = alarmDays,
        isActive = isActive,
    )
}

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
