package fyi.manpreet.brightstart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmActive
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val repository: AlarmRepository,
) : ViewModel() {

    init {
        onEvent(HomeEvent.FetchAlarms)
    }

    val alarms: StateFlow<List<Alarm>>
        field = MutableStateFlow(emptyList())

    val ringtonePickerState: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val ringtonePickerData: StateFlow<Pair<String?, String?>?>
        field = MutableStateFlow(null)

    fun updateRingtoneState(state: Boolean) {
        println("Ringtone state: $state")
        ringtonePickerState.update { state }
    }

    fun updateRingtoneData(data: Pair<String?, String?>?) {
        println("Ringtone data: $data")
        ringtonePickerData.update { data }
        updateRingtoneState(false)
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
                    println("Delete Before all alarms: ${alarms.value.joinToString()}")
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

    private fun toggleAlarm(alarm: Alarm, status: Boolean) {
        viewModelScope.launch {
            val updatedAlarms = alarms.value.map { currentAlarm ->
                if (alarm == currentAlarm) {
                    val updatedAlarm = currentAlarm.copy(isActive = AlarmActive(status))
                    println("Alarm to toggle: $updatedAlarm")
                    repository.updateAlarm(updatedAlarm)
                    updatedAlarm
                } else {
                    currentAlarm
                }
            }
            alarms.update { updatedAlarms }
            Logger.d("Updated alarms: ${alarms.value.joinToString()}")
        }
    }
}