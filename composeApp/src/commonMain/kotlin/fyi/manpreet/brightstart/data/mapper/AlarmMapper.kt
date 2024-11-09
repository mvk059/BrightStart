package fyi.manpreet.brightstart.data.mapper

import fyi.manpreet.brightstart.data.database.AlarmTable
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.data.model.DaysEnum

fun Alarm.toAlarmTable(): AlarmTable {
    return AlarmTable(
        id = id,
        time = time,
        name = name,
        ringtoneReference = ringtoneReference,
        ringtoneName = ringtoneName,
        volume = volume,
        vibrationStatus = vibrationStatus,
        isActive = isActive,
        monday = alarmDays.first { it.id == DaysEnum.MONDAY }.isSelected,
        tuesday = alarmDays.first { it.id == DaysEnum.TUESDAY }.isSelected,
        wednesday = alarmDays.first { it.id == DaysEnum.WEDNESDAY }.isSelected,
        thursday = alarmDays.first { it.id == DaysEnum.THURSDAY }.isSelected,
        friday = alarmDays.first { it.id == DaysEnum.FRIDAY }.isSelected,
        saturday = alarmDays.first { it.id == DaysEnum.SATURDAY }.isSelected,
        sunday = alarmDays.first { it.id == DaysEnum.SUNDAY }.isSelected,
    )
}

fun List<AlarmTable>.toAlarm() =
    map { alarmTable -> alarmTable.toAlarm() }

fun AlarmTable.toAlarm() = Alarm(
    id = id,
    time = time,
    name = name,
    ringtoneReference = ringtoneReference,
    ringtoneName = ringtoneName,
    volume = volume,
    vibrationStatus = vibrationStatus,
    alarmDays = this.toAlarmDays(),
    isActive = isActive,
)

fun AlarmTable.toAlarmDays(): List<AlarmDays> =
    buildList {
        add(AlarmDays(id = DaysEnum.MONDAY, day = "Mon", isSelected = monday))
        add(AlarmDays(id = DaysEnum.TUESDAY, day = "Tue", isSelected = tuesday))
        add(AlarmDays(id = DaysEnum.WEDNESDAY, day = "Wed", isSelected = wednesday))
        add(AlarmDays(id = DaysEnum.THURSDAY, day = "Thu", isSelected = thursday))
        add(AlarmDays(id = DaysEnum.FRIDAY, day = "Fri", isSelected = friday))
        add(AlarmDays(id = DaysEnum.SATURDAY, day = "Sat", isSelected = saturday))
        add(AlarmDays(id = DaysEnum.SUNDAY, day = "Sun", isSelected = sunday))
    }

