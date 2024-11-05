package fyi.manpreet.brightstart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AlarmRepository,
) : ViewModel() {

    init {
        onEvent(HomeEvent.FetchAlarms)
    }

    val alarms: StateFlow<List<Alarm>>
        field = MutableStateFlow(emptyList())

    val alarmDays: StateFlow<AlarmDays>
        field = MutableStateFlow(AlarmDays())

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.AddAlarm -> {
                viewModelScope.launch {
                    val days = AlarmDays(monday = true)
                    val alarm = Alarm(
                        time = "08:00",
                        name = "Alarm 1",
                        ringtoneReference = "",
                        vibrationStatus = true,
                        alarmDays = days,
                    )
                    val alarmId = repository.insertAlarm(alarm, days)
                    Logger.d { "Alarm inserted with ID: $alarmId" }

                    onEvent(HomeEvent.FetchAlarms)
                }
            }

            HomeEvent.FetchAlarms -> {
                viewModelScope.launch {
                    val allAlarms = repository.fetchAllAlarms()
                    alarms.update { allAlarms }
                    Logger.d { "Alarms: ${allAlarms.joinToString()}" }
                }
            }

            HomeEvent.FetchAlarmDays -> {
                viewModelScope.launch {
                    TODO()
                }
            }
        }
    }

}