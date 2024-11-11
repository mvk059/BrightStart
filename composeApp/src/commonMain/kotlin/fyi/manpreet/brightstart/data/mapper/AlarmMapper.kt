package fyi.manpreet.brightstart.data.mapper

import fyi.manpreet.brightstart.data.database.AlarmTable
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.data.model.AlarmActive
import fyi.manpreet.brightstart.data.model.AlarmDaySelected
import fyi.manpreet.brightstart.data.model.AlarmDayTitle
import fyi.manpreet.brightstart.data.model.AlarmName
import fyi.manpreet.brightstart.data.model.AlarmTime
import fyi.manpreet.brightstart.data.model.DaysEnum
import fyi.manpreet.brightstart.data.model.RingtoneName
import fyi.manpreet.brightstart.data.model.RingtoneReference
import fyi.manpreet.brightstart.data.model.VibrationStatus
import fyi.manpreet.brightstart.data.model.Volume

fun Alarm.toAlarmTable(): AlarmTable {
    return AlarmTable(
        id = id,
        time = time.value,
        name = name.value,
        ringtoneReference = ringtoneReference.value,
        ringtoneName = ringtoneName.value,
        volume = volume.value,
        vibrationStatus = vibrationStatus.value,
        isActive = isActive.value,
        monday = alarmDays.first { it.id == DaysEnum.MONDAY }.isSelected.value,
        tuesday = alarmDays.first { it.id == DaysEnum.TUESDAY }.isSelected.value,
        wednesday = alarmDays.first { it.id == DaysEnum.WEDNESDAY }.isSelected.value,
        thursday = alarmDays.first { it.id == DaysEnum.THURSDAY }.isSelected.value,
        friday = alarmDays.first { it.id == DaysEnum.FRIDAY }.isSelected.value,
        saturday = alarmDays.first { it.id == DaysEnum.SATURDAY }.isSelected.value,
        sunday = alarmDays.first { it.id == DaysEnum.SUNDAY }.isSelected.value,
    )
}

fun List<AlarmTable>.toAlarm() =
    map { alarmTable -> alarmTable.toAlarm() }

fun AlarmTable.toAlarm() = Alarm(
    id = id,
    time = AlarmTime(time),
    name = AlarmName(name),
    ringtoneReference = RingtoneReference(ringtoneReference),
    ringtoneName = RingtoneName(ringtoneName),
    volume = Volume(volume),
    vibrationStatus = VibrationStatus(vibrationStatus),
    alarmDays = this.toAlarmDays(),
    isActive = AlarmActive(isActive),
)

fun AlarmTable.toAlarmDays(): List<AlarmDays> =
    buildList {
        add(
            AlarmDays(
                id = DaysEnum.MONDAY,
                AlarmDayTitle("Sun"),
                isSelected = AlarmDaySelected(false)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.TUESDAY,
                AlarmDayTitle("Mon"),
                isSelected = AlarmDaySelected(false)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.WEDNESDAY,
                AlarmDayTitle("Tue"),
                isSelected = AlarmDaySelected(false)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.THURSDAY,
                AlarmDayTitle("Wed"),
                isSelected = AlarmDaySelected(false)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.FRIDAY,
                AlarmDayTitle("Thu"),
                isSelected = AlarmDaySelected(false)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.SATURDAY,
                AlarmDayTitle("Fri"),
                isSelected = AlarmDaySelected(false)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.SUNDAY,
                AlarmDayTitle("Sat"),
                isSelected = AlarmDaySelected(false)
            )
        )
    }
