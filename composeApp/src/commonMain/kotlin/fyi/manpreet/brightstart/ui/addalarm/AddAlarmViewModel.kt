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
import fyi.manpreet.brightstart.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.model.AlarmConstants
import fyi.manpreet.brightstart.ui.model.AlarmTimeSelector
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import fyi.manpreet.brightstart.ui.model.TimePeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

class AddAlarmViewModel(
    private val alarmScheduler: AlarmScheduler,
    private val repository: AlarmRepository,
) : ViewModel() {

    val currentAlarm: StateFlow<Alarm>
        field = MutableStateFlow(initCurrentAlarm())

    val timeSelector: StateFlow<AlarmTimeSelector>
        field  = MutableStateFlow(AlarmTimeSelector())

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
    }

    fun onTimeScrollingUpdate() {
        val hour = timeSelector.value.hours[timeSelector.value.selectedHourIndex]
        // TODO Handle for 24 hours
        val minutes = timeSelector.value.minutes[timeSelector.value.selectedMinuteIndex]
        timeSelector.update {
            it.copy(
                selectedTime = AlarmTimeSelector.AlarmSelectedTime(
                    hour = hour,
                    minute = minutes,
                )
            )
        }

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
            localTime = Clock.System.now().plus(10.seconds)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            time = AlarmTime(""),
            name = AlarmName(""), // TODO Get text from strings or use default while adding alarm
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
            add(Alarm.AlarmDays(id = DaysEnum.MONDAY, day = AlarmDayTitle("Sun"), isSelected = AlarmDaySelected(false)))
            add(Alarm.AlarmDays(id = DaysEnum.TUESDAY, day = AlarmDayTitle("Mon"), isSelected = AlarmDaySelected(false)))
            add(Alarm.AlarmDays(id = DaysEnum.WEDNESDAY, day = AlarmDayTitle("Tue"), isSelected = AlarmDaySelected(false)))
            add(Alarm.AlarmDays(id = DaysEnum.THURSDAY, day = AlarmDayTitle("Wed"), isSelected = AlarmDaySelected(false)))
            add(Alarm.AlarmDays(id = DaysEnum.FRIDAY, day = AlarmDayTitle("Thu"), isSelected = AlarmDaySelected(false)))
            add(Alarm.AlarmDays(id = DaysEnum.SATURDAY, day = AlarmDayTitle("Fri"), isSelected = AlarmDaySelected(false)))
            add(Alarm.AlarmDays(id = DaysEnum.SUNDAY, day = AlarmDayTitle("Sat"), isSelected = AlarmDaySelected(false)))
        }

    private fun addAlarm() {
        Logger.d("Alarm addAlarm")
        val currentAlarm = currentAlarm.value
        val time = AlarmTime(currentAlarm.localTime.toString()) // TODO Construct time from localTime
        val name = AlarmName(currentAlarm.name.value.ifEmpty { AlarmName("New Alarm") }.toString())
        val ringtoneReference = currentAlarm.ringtoneReference
        val ringtoneName = currentAlarm.ringtoneName

        require(ringtoneReference.value.isNotEmpty()) { "Ringtone Reference is empty" }
        require(ringtoneName.value.isNotEmpty()) { "Ringtone Reference is empty" }

        val alarm = Alarm(
            localTime = currentAlarm.localTime,
            time = time,
            name = name, // TODO Get text from strings
            ringtoneReference = ringtoneReference,
            ringtoneName = ringtoneName,
            volume = currentAlarm.volume,
            vibrationStatus = currentAlarm.vibrationStatus,
            alarmDays = currentAlarm.alarmDays,
            isActive = AlarmActive(true),
        )

        Logger.d("Alarm schedule start")
        viewModelScope.launch {
            alarmScheduler.schedule(alarm)
            repository.insertAlarm(alarm)
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
}