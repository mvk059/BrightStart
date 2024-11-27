package fyi.manpreet.brightstart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.mapper.calculateTimeBetweenWithText
import fyi.manpreet.brightstart.data.mapper.formatLocalDateTimeToHHMM
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmActive
import fyi.manpreet.brightstart.data.model.AlarmTime
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.platform.scheduler.AlarmInteraction
import fyi.manpreet.brightstart.platform.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

class HomeViewModel(
    private val alarmScheduler: AlarmScheduler,
    private val repository: AlarmRepository,
) : ViewModel(), AlarmInteraction {

    init {
        onEvent(HomeEvent.FetchAlarms)
    }

    private val _alarms: MutableStateFlow<List<Alarm>> = MutableStateFlow(emptyList())
    val alarms: StateFlow<List<Alarm>> = _alarms.asStateFlow()

    private val _ringtonePickerState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val ringtonePickerState: StateFlow<Boolean> = _ringtonePickerState.asStateFlow()

    private val _ringtonePickerData: MutableStateFlow<Pair<String?, String?>?> =
        MutableStateFlow(null)
    val ringtonePickerData: StateFlow<Pair<String?, String?>?> = _ringtonePickerData.asStateFlow()

    override fun onAlarmDismiss(id: Long) {
        println("Dismiss alarm $id")
        val scope = if (viewModelScope.isActive) viewModelScope else CoroutineScope(Dispatchers.IO)
        scope.launch {
            require(id != -1L) { "Invalid alarm id in onAlarmDismiss" }
            val alarm = repository.fetchAlarmById(id) ?: return@launch
            alarmScheduler.cancel(alarm)
            val updatedAlarm = alarm.copy(isActive = AlarmActive(false))
            repository.updateAlarm(updatedAlarm)
            _alarms.update {
                it.map { current ->
                    if (current.id == id) updatedAlarm
                    else current
                }
            }
            println("Alarm dismissed: ${_alarms.value.joinToString()}")
        }
    }

    override fun onAlarmSnooze(id: Long) {
        println("Snooze alarm $id")
        val scope = if (viewModelScope.isActive) viewModelScope else CoroutineScope(Dispatchers.IO)
        scope.launch {
            require(id != -1L) { "Invalid alarm id in onAlarmSnooze" }
            val alarm = repository.fetchAlarmById(id) ?: return@launch

            val updatedLocalTimeInstant =
                alarm.localTime.toInstant(TimeZone.currentSystemDefault()).plus(1.minutes)
            val updatedLocalTime =
                updatedLocalTimeInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val updatedAlarm = alarm.copy(
                localTime = updatedLocalTime,
                time = AlarmTime(
                    updatedLocalTime.formatLocalDateTimeToHHMM(),
                    Hour(updatedLocalTime.hour),
                    Minute(updatedLocalTime.minute)
                )
            )

            alarmScheduler.cancel(alarm)
            alarmScheduler.schedule(updatedAlarm)
            repository.updateAlarm(updatedAlarm)
            _alarms.update {
                it.map { current ->
                    if (current.id == id) updatedAlarm
                    else current
                }
            }
            println("Alarm snoozed: ${_alarms.value.joinToString()}")
        }
    }

    override suspend fun getAlarm(id: Long): Alarm? {
        return repository.fetchAlarmById(id)
    }

    override suspend fun getAllAlarms(): List<Alarm> {
        return repository.fetchAllAlarms()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchAlarms -> {
                viewModelScope.launch {
                    delay(100.milliseconds)
                    val allAlarms = repository.fetchAllAlarms()
                    _alarms.update { allAlarms }
                    Logger.d { "Alarms: ${_alarms.value.joinToString()}" }
                }
            }

            is HomeEvent.DeleteAlarm -> {
                viewModelScope.launch {
                    Logger.d("Delete Before all alarms: ${_alarms.value.joinToString()}")
                    val alarmId = _alarms.value.firstOrNull { it.id == event.alarm.id }
                    requireNotNull(alarmId) { "Alarm not found" }
                    repository.deleteAlarm(event.alarm)
                    alarmScheduler.cancel(event.alarm)
                    _alarms.update {
                        it.toMutableList().apply {
                            remove(event.alarm)
                        }
                    }
                }
            }

            is HomeEvent.ToggleAlarm -> toggleAlarm(event.alarm, event.status)
        }
    }

    fun updateRingtoneState(state: Boolean) {
        println("Ringtone state: $state")
        _ringtonePickerState.update { state }
    }

    fun updateRingtoneData(data: Pair<String?, String?>?) {
        println("Ringtone data: $data")
        _ringtonePickerData.update { data }
        updateRingtoneState(false)
    }

    private fun toggleAlarm(alarm: Alarm, status: Boolean) {
        viewModelScope.launch {
            // Schedule or cancel alarm
            if (status) alarmScheduler.schedule(alarm)
            else alarmScheduler.cancel(alarm)

            val updatedAlarms = _alarms.value.map { currentAlarm ->
                if (alarm == currentAlarm) {
                    val timeLeftForAlarm =
                        if (status) calculateTimeBetweenWithText(
                            selectedDateTime = currentAlarm.localTime.toInstant(TimeZone.currentSystemDefault()),
                            alarmDays = currentAlarm.alarmDays,
                            text = ""
                        )
                        else ""
                    val updatedAlarm = currentAlarm.copy(
                        isActive = AlarmActive(status),
                        timeLeftForAlarm = timeLeftForAlarm
                    )
                    repository.updateAlarm(updatedAlarm)
                    updatedAlarm
                } else {
                    currentAlarm
                }
            }
            _alarms.update { updatedAlarms }
        }
    }
}
