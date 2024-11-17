package fyi.manpreet.brightstart.ui.addalarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.model.Alarm
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
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.platform.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.model.AlarmConstants
import fyi.manpreet.brightstart.ui.model.AlarmTimeSelector
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import fyi.manpreet.brightstart.ui.model.TimePeriod
import fyi.manpreet.brightstart.ui.model.TimePeriodValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit

class AddAlarmViewModel(
    private val alarmScheduler: AlarmScheduler,
    private val repository: AlarmRepository,
) : ViewModel() {

    val currentAlarm: StateFlow<Alarm>
        field = MutableStateFlow(initCurrentAlarm())

    val timeSelector: StateFlow<AlarmTimeSelector>
        field = MutableStateFlow(AlarmTimeSelector())

    val repeatDays: StateFlow<String>
        field = MutableStateFlow("")

    fun updateCurrentAlarm(alarmId: Long?) {
        if (alarmId == null) return
        viewModelScope.launch {
            val alarm = repository.fetchAlarmById(alarmId)
            if (alarm == null) return@launch
            currentAlarm.update { alarm }
        }
    }

    fun onEvent(event: AddAlarmEvent) {
        when (event) {
            AddAlarmEvent.AddAlarm -> addAlarm()
            is AddAlarmEvent.SoundUpdate -> onSoundUpdate(event.data)
            is AddAlarmEvent.VolumeUpdate -> onVolumeUpdate(event.volume)
            is AddAlarmEvent.VibrateUpdate -> onVibrateUpdate(event.vibrationStatus)
            is AddAlarmEvent.NameUpdate -> onAlarmNameUpdate(event.name)
            is AddAlarmEvent.RepeatUpdate -> onRepeatItemClick(event.item)
        }
    }

    fun onHourIndexUpdate(hour: Int) {
        timeSelector.update { it.copy(selectedHourIndex = hour) }
    }

    fun onMinuteIndexUpdate(minute: Int) {
        timeSelector.update { it.copy(selectedMinuteIndex = minute) }
    }

    fun onTimePeriodIndexUpdate(timePeriod: Int) {
        timeSelector.update { it.copy(selectedTimePeriodIndex = timePeriod) }
        onTimeScrollingUpdate() // onTimeScrollingUpdate is not called by default on time period change
    }

    fun onTimeScrollingUpdate() {
        val hour = timeSelector.value.hours[timeSelector.value.selectedHourIndex]
        // TODO Handle for 24 hours
        val minutes = timeSelector.value.minutes[timeSelector.value.selectedMinuteIndex]
        val timePeriod = timeSelector.value.timePeriod[timeSelector.value.selectedTimePeriodIndex]

        onTimeSelectionUpdate(hour, minutes, timePeriod)

//        val time = timeSelector.value?.let {
//            var hour = it.hours[it.selectedHourIndex].toInt()
//            if (!it.is24Hour) {
//                hour = hour % 12 + if (it.selectedTimeOfDayIndex == 1) 12 else 0
//            }
//            TimePickerTime(
//                hour,
//                it.minutes[it.selectedMinuteIndex].toInt()
//            )
//        }
//        return time
    }

    private fun initCurrentAlarm() =
        Alarm(
            localTime = Clock.System.now()//.plus(10.seconds)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            time = AlarmTime(""),
            name = AlarmName("Alarm"), // TODO Get text from strings or use default while adding alarm
            timePeriod = TimePeriod(TimePeriodValue.AM),
            ringtoneReference = RingtoneReference(""),
            ringtoneName = RingtoneName(""),
            volume = Volume(AlarmConstants.VOLUME), // TODO Should set default volume of phone
            vibrationStatus = VibrationStatus(AlarmConstants.VIBRATION_STATUS),  // TODO Should set default vibration status of phone
            alarmDays = initAlarmDays(),
            isActive = AlarmActive(AlarmConstants.IS_ACTIVE),
        )

    // TODO Get text from strings
    private fun initAlarmDays() =
        buildList {
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.MONDAY,
                    day = AlarmDayTitle("Sun"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.TUESDAY,
                    day = AlarmDayTitle("Mon"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.WEDNESDAY,
                    day = AlarmDayTitle("Tue"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.THURSDAY,
                    day = AlarmDayTitle("Wed"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.FRIDAY,
                    day = AlarmDayTitle("Thu"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.SATURDAY,
                    day = AlarmDayTitle("Fri"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.SUNDAY,
                    day = AlarmDayTitle("Sat"),
                    isSelected = AlarmDaySelected(false)
                )
            )
        }

    private fun addAlarm() {
        Logger.d("Alarm addAlarm")
        val currentAlarm = currentAlarm.value

        val time =
            AlarmTime(formatLocalDateTimeToHHMM(currentAlarm.localTime)) // TODO Construct time from localTime
        val name = AlarmName(currentAlarm.name.value.ifEmpty { AlarmName("New Alarm") }.toString())
        val ringtoneReference = currentAlarm.ringtoneReference
        val ringtoneName = currentAlarm.ringtoneName

        require(ringtoneReference.value.isNotEmpty()) { "Ringtone Reference is empty" }
        require(ringtoneName.value.isNotEmpty()) { "Ringtone Reference is empty" }

        val alarm = Alarm(
            localTime = currentAlarm.localTime, //Clock.System.now().plus(10.seconds).toLocalDateTime(TimeZone.currentSystemDefault()),
            time = time,
            name = name, // TODO Get text from strings
            timePeriod = currentAlarm.timePeriod,
            ringtoneReference = ringtoneReference,
            ringtoneName = ringtoneName,
            volume = currentAlarm.volume,
            vibrationStatus = currentAlarm.vibrationStatus,
            alarmDays = currentAlarm.alarmDays,
            isActive = AlarmActive(true),
        )

        Logger.d("Alarm schedule start")
        viewModelScope.launch {
            val id = repository.insertAlarm(alarm)
            println("Add Alarm id: $id")
            alarmScheduler.schedule(alarm.copy(id = id))
        }
    }

    private fun onSoundUpdate(data: Pair<String?, String?>) {
        val ringtoneReference = data.first
        val ringtoneName = data.second
        requireNotNull(ringtoneReference) { "Ringtone Reference is null" }
        requireNotNull(ringtoneName) { "Ringtone Name is null" }
        // TODO Handle ringtone being null when cancelled

        currentAlarm.update {
            it.copy(
                ringtoneReference = RingtoneReference(ringtoneReference),
                ringtoneName = RingtoneName(ringtoneName)
            )
        }
    }

    private fun onVolumeUpdate(volume: Int) {
        currentAlarm.update { it.copy(volume = Volume(volume)) }
    }

    private fun onVibrateUpdate(vibrate: Boolean) {
        currentAlarm.update { it.copy(vibrationStatus = VibrationStatus(vibrate)) }
    }

    private fun onAlarmNameUpdate(name: String) {
        currentAlarm.update { it.copy(name = AlarmName(name)) }
    }

    private fun onRepeatItemClick(newItem: Alarm.AlarmDays) {
        val alarmDays = currentAlarm.value.alarmDays.map { oldItem ->
            if (oldItem == newItem) oldItem.copy(isSelected = AlarmDaySelected(!oldItem.isSelected.value))
            else oldItem
        }
        currentAlarm.update { it.copy(alarmDays = alarmDays) }
    }

    private fun onTimeSelectionUpdate(hour: Hour, minutes: Minute, timePeriod: TimePeriod) {
        // Get system date time
        val systemLocalDateTime =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        // Convert to instant with current time zone
        val systemDateTimeInstant: Instant =
            systemLocalDateTime.toInstant(TimeZone.currentSystemDefault())

        // Get today's date
        val todayLocalDate = systemLocalDateTime.date
        // Create selected LocalTime
        val selectedHour =
            if (timePeriod.value == TimePeriodValue.AM) hour.value
            else hour.value + 12
        val selectedLocalTime =
            LocalTime(hour = selectedHour, minute = minutes.value, second = 0, nanosecond = 0)
        // Create selected LocalDateTime
        var selectedLocalDateTime = todayLocalDate.atTime(selectedLocalTime)
        // Create selected Instant with default time zone. This will be converted to UTC time zone
        var selectedLocalDateTimeInstant =
            selectedLocalDateTime.toInstant(TimeZone.currentSystemDefault())

        // Compare to see if selected time is less than current time. If so, add a day to schedule the alarm for the next day
        if (selectedLocalTime < systemLocalDateTime.time) {
            // If selected time is less than current time, set it for next day
            val updatedLocalDate = todayLocalDate.plus(DatePeriod(days = 1))
            selectedLocalDateTime = updatedLocalDate.atTime(selectedLocalTime)
            selectedLocalDateTimeInstant =
                selectedLocalDateTime.toInstant(TimeZone.currentSystemDefault())
        }

        // Create formatted string for time left for alarm
        val timeLeftForAlarm = formatDuration(selectedLocalDateTimeInstant, systemDateTimeInstant)
        currentAlarm.update {
            it.copy(
                localTime = selectedLocalDateTime,
                timePeriod = timePeriod,
                timeLeftForAlarm = timeLeftForAlarm,
            )
        }

        timeSelector.update {
            it.copy(
                selectedTime = AlarmTimeSelector.AlarmSelectedTime(hour = hour, minute = minutes)
            )
        }
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun formatLocalDateTimeToHHMM(localDateTime: LocalDateTime): String {
        return localDateTime.format(LocalDateTime.Format {
            byUnicodePattern("HH:mm")
        })
    }

    private fun formatDuration(selectedDateTime: Instant, systemDateTime: Instant): String {
        val duration = selectedDateTime.minus(systemDateTime)

        val days = duration.toInt(DurationUnit.DAYS)
        val hours = duration.toInt(DurationUnit.HOURS) % 24
        val minutes = duration.toInt(DurationUnit.MINUTES) % 60

        return buildString {
            append("Alarm in ")
            if (days > 0) append("${days}d ")
            if (hours > 0 || days > 0) append("${hours}h ")
            append("${minutes}min")
        }.trim()
    }

    private fun formatTime(selectedDateTime: LocalDateTime): String {
        val hour = selectedDateTime.hour
        val minute = selectedDateTime.minute
        val timePeriod = if (hour < 12) "AM" else "PM"
        val formattedHour = if (hour > 12) hour - 12 else hour
        return "$formattedHour:$minute $timePeriod"
    }

}