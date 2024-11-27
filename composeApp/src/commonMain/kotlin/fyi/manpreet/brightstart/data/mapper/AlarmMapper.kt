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
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import fyi.manpreet.brightstart.ui.model.TimePeriod
import fyi.manpreet.brightstart.ui.model.TimePeriodValue
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun Alarm.toAlarmTable(): AlarmTable {
    return AlarmTable(
        id = id,
        localDateTime = localTime.toString(),
        time = time.value,
        timePeriod = timePeriod.value.name,
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
    localTime = LocalDateTime.parse(localDateTime),
    time = AlarmTime(
        value = time,
        hour = Hour(LocalDateTime.parse(localDateTime).hour),
        minute = Minute(LocalDateTime.parse(localDateTime).minute)
    ),
    timePeriod =
    if (timePeriod.lowercase() == TimePeriodValue.AM.name.lowercase()) TimePeriod(TimePeriodValue.AM)
    else TimePeriod(TimePeriodValue.PM),
    name = AlarmName(name),
    ringtoneReference = RingtoneReference(ringtoneReference),
    ringtoneName = RingtoneName(ringtoneName),
    volume = Volume(volume),
    vibrationStatus = VibrationStatus(vibrationStatus),
    alarmDays = this.toAlarmDays(),
    repeatDays = this.toAlarmDays().constructRepeatDays(LocalDateTime.parse(localDateTime)),
    isActive = AlarmActive(isActive),
    timeLeftForAlarm = calculateTimeBetweenWithText(
        selectedDateTime = LocalDateTime.parse(localDateTime)
            .toInstant(TimeZone.currentSystemDefault()),
        alarmDays = this.toAlarmDays(),
        text = ""
    ),
    icon = LocalDateTime.parse(localDateTime).getIconForTime()
)

fun AlarmTable.toAlarmDays(): List<AlarmDays> =
    buildList {
        add(
            AlarmDays(
                id = DaysEnum.MONDAY,
                AlarmDayTitle("Mon"),
                isSelected = AlarmDaySelected(this@toAlarmDays.monday)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.TUESDAY,
                AlarmDayTitle("Tue"),
                isSelected = AlarmDaySelected(this@toAlarmDays.tuesday)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.WEDNESDAY,
                AlarmDayTitle("Wed"),
                isSelected = AlarmDaySelected(this@toAlarmDays.wednesday)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.THURSDAY,
                AlarmDayTitle("Thu"),
                isSelected = AlarmDaySelected(this@toAlarmDays.thursday)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.FRIDAY,
                AlarmDayTitle("Fri"),
                isSelected = AlarmDaySelected(this@toAlarmDays.friday)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.SATURDAY,
                AlarmDayTitle("Sat"),
                isSelected = AlarmDaySelected(this@toAlarmDays.saturday)
            )
        )
        add(
            AlarmDays(
                id = DaysEnum.SUNDAY,
                AlarmDayTitle("Sun"),
                isSelected = AlarmDaySelected(this@toAlarmDays.sunday)
            )
        )
    }
