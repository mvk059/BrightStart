package fyi.manpreet.brightstart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.mapper.formatLocalDateTimeToHHMM
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmActive
import fyi.manpreet.brightstart.data.model.AlarmTime
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.platform.scheduler.AlarmInteraction
import fyi.manpreet.brightstart.platform.scheduler.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val alarms: StateFlow<List<Alarm>>
        field = MutableStateFlow(emptyList())

    val ringtonePickerState: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val ringtonePickerData: StateFlow<Pair<String?, String?>?>
        field = MutableStateFlow(null)

    val alarmTriggerState: StateFlow<Alarm?>
        field = MutableStateFlow(null)

    override fun onAlarmDismiss(id: Long) {
        println("Dismiss alarm $id")
        val scope = if (viewModelScope.isActive) viewModelScope else CoroutineScope(Dispatchers.IO)
        scope.launch {
            require(id != -1L) { "Invalid alarm id in onAlarmDismiss" }
            val alarm = repository.fetchAlarmById(id)
            requireNotNull(alarm) { "Alarm null in onAlarmDismiss $id" } // TODO Update message
            alarmScheduler.cancel(alarm)
            val updatedAlarm = alarm.copy(isActive = AlarmActive(false))
            repository.updateAlarm(updatedAlarm)
            alarms.update {
                it.toMutableList().apply {
                    set(indexOf(alarm), updatedAlarm)
                }
            }
            println("Alarm dismissed: ${alarms.value.joinToString()}")
        }
    }

    override fun onAlarmSnooze(id: Long) {
        println("Snooze alarm $id")
        val scope = if (viewModelScope.isActive) viewModelScope else CoroutineScope(Dispatchers.IO)
        scope.launch {
            require(id != -1L) { "Invalid alarm id in onAlarmSnooze" }
            val alarm = repository.fetchAlarmById(id)
            requireNotNull(alarm) { "Alarm null in onAlarmSnooze $id" } // TODO Update message

            val updatedLocalTimeInstant =
                alarm.localTime.toInstant(TimeZone.currentSystemDefault()).plus(1.minutes)
            val updatedLocalTime =
                updatedLocalTimeInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val updatedAlarm = alarm.copy(
                localTime = updatedLocalTime,
                time = AlarmTime(updatedLocalTime.formatLocalDateTimeToHHMM())
            )

            alarmScheduler.cancel(alarm)
            alarmScheduler.schedule(updatedAlarm)
            repository.updateAlarm(updatedAlarm)
            alarms.update {
                it.toMutableList().apply {
                    set(indexOf(alarm), updatedAlarm)
                }
            }
            println("Alarm snoozed: ${alarms.value.joinToString()}")
        }
    }

    override suspend fun getAlarm(id: Long): Alarm? {
        println("Get alarm $id")
        return repository.fetchAlarmById(id)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchAlarms -> {
                viewModelScope.launch {
                    delay(100.milliseconds)
                    val allAlarms = repository.fetchAllAlarms()
//                    alarms.update { emptyList() }
                    alarms.update { allAlarms }
                    Logger.d { "Alarms: ${alarms.value.joinToString()}" }
                }
            }

            is HomeEvent.DeleteAlarm -> {
                viewModelScope.launch {
                    Logger.d("Delete Before all alarms: ${alarms.value.joinToString()}")
                    val alarmId = alarms.value.firstOrNull { it.id == event.alarm.id }
                    requireNotNull(alarmId) { "Alarm not found" }
                    repository.deleteAlarm(event.alarm)
                    alarms.update {
                        it.toMutableList().apply {
                            remove(event.alarm)
                        }
                    }
                }
            }

            is HomeEvent.ToggleAlarm -> toggleAlarm(event.alarm, event.status)
        }
    }

    fun resetAlarmTriggerState() {
        alarmTriggerState.update { null }
    }

    fun updateRingtoneState(state: Boolean) {
        println("Ringtone state: $state")
        ringtonePickerState.update { state }
    }

    fun updateRingtoneData(data: Pair<String?, String?>?) {
        println("Ringtone data: $data")
        ringtonePickerData.update { data }
        updateRingtoneState(false)
    }

    fun onStopAlarm(alarm: Alarm) {
        println("Stop alarm")
        resetAlarmTriggerState()
    }

    fun onSnoozeAlarm(alarm: Alarm) {
        println("Snooze alarm")
    }

    private fun toggleAlarm(alarm: Alarm, status: Boolean) {
        viewModelScope.launch {
            // Schedule or cancel alarm
            if (status) alarmScheduler.schedule(alarm)
            else alarmScheduler.cancel(alarm)

            val updatedAlarms = alarms.value.map { currentAlarm ->
                if (alarm == currentAlarm) {
                    val updatedAlarm = currentAlarm.copy(isActive = AlarmActive(status))
                    repository.updateAlarm(updatedAlarm)
                    updatedAlarm
                } else {
                    currentAlarm
                }
            }
            alarms.update { updatedAlarms }
        }
    }
}