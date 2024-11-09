package fyi.manpreet.brightstart.ui.addalarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.DaysEnum
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.model.AlarmConstants
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

    private fun initCurrentAlarm() =
        Alarm(
            localTime = Clock.System.now().plus(10.seconds)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            time = "",
            name = "", // TODO Get text from strings or use default while adding alarm
            ringtoneReference = "",
            ringtoneName = "",
            volume = AlarmConstants.VOLUME,
            vibrationStatus = AlarmConstants.VIBRATION_STATUS,
            alarmDays = initAlarmDays(),
            isActive = AlarmConstants.IS_ACTIVE,
        )

    // TODO Get text from strings
    private fun initAlarmDays() =
        buildList {
            add(Alarm.AlarmDays(id = DaysEnum.MONDAY, day = "Sun", isSelected = false))
            add(Alarm.AlarmDays(id = DaysEnum.TUESDAY, day = "Mon", isSelected = false))
            add(Alarm.AlarmDays(id = DaysEnum.WEDNESDAY, day = "Tue", isSelected = false))
            add(Alarm.AlarmDays(id = DaysEnum.THURSDAY, day = "Wed", isSelected = false))
            add(Alarm.AlarmDays(id = DaysEnum.FRIDAY, day = "Thu", isSelected = false))
            add(Alarm.AlarmDays(id = DaysEnum.SATURDAY, day = "Fri", isSelected = false))
            add(Alarm.AlarmDays(id = DaysEnum.SUNDAY, day = "Sat", isSelected = false))
        }

    private fun addAlarm() {
        Logger.d("Alarm addAlarm")
        val currentAlarm = currentAlarm.value
        val time = currentAlarm.localTime.toString() // TODO Construct time from localTime
        val ringtoneReference = currentAlarm.ringtoneReference
        val ringtoneName = currentAlarm.ringtoneName

        require(ringtoneReference.isNotEmpty()) { "Ringtone Reference is empty" }
        require(ringtoneName.isNotEmpty()) { "Ringtone Reference is empty" }

        val alarm = Alarm(
            localTime = currentAlarm.localTime,
            time = time,
            name = currentAlarm.name.ifEmpty { "New Alarm" }, // TODO Get text from strings
            ringtoneReference = ringtoneReference,
            ringtoneName = ringtoneName,
            volume = currentAlarm.volume,
            vibrationStatus = currentAlarm.vibrationStatus,
            alarmDays = currentAlarm.alarmDays,
            isActive = true,
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

        currentAlarm.update {
            it.copy(
                ringtoneReference = ringtoneReference,
                ringtoneName = ringtoneName
            )
        }
    }

    private fun onVolumeUpdate(volume: Int) {
        currentAlarm.update { it.copy(volume = volume) }
    }

    private fun onVibrateUpdate(vibrate: Boolean) {
        currentAlarm.update { it.copy(vibrationStatus = vibrate) }
    }

    private fun onAlarmNameUpdate(name: String) {
        currentAlarm.update { it.copy(name = name) }
    }

    private fun onRepeatItemClick(newItem: Alarm.AlarmDays) {
        val alarmDays = currentAlarm.value.alarmDays.map { oldItem ->
            if (oldItem == newItem) oldItem.copy(isSelected = !oldItem.isSelected)
            else oldItem
        }
        currentAlarm.update { it.copy(alarmDays = alarmDays) }
    }
}