package fyi.manpreet.brightstart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.ui.model.AlarmDaysItem
import fyi.manpreet.brightstart.ui.model.AlarmItem
import fyi.manpreet.brightstart.ui.model.DaysEnum
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

    val alarmItem: StateFlow<AlarmItem?>
        field = MutableStateFlow(null)

    val alarmDaysItem: StateFlow<List<AlarmDaysItem>>
        field = MutableStateFlow(initAlarmDays())

    val repeatDays: StateFlow<String>
        field = MutableStateFlow("")

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
                        isActive = true,
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

    // TODO Get text from strings
    private fun initAlarmDays() =
        buildList {
            add(AlarmDaysItem(id = DaysEnum.MONDAY, day = "Sun", isSelected = false))
            add(AlarmDaysItem(id = DaysEnum.TUESDAY, day = "Mon", isSelected = false))
            add(AlarmDaysItem(id = DaysEnum.WEDNESDAY, day = "Tue", isSelected = false))
            add(AlarmDaysItem(id = DaysEnum.THURSDAY, day = "Wed", isSelected = false))
            add(AlarmDaysItem(id = DaysEnum.FRIDAY, day = "Thu", isSelected = false))
            add(AlarmDaysItem(id = DaysEnum.SATURDAY, day = "Fri", isSelected = false))
            add(AlarmDaysItem(id = DaysEnum.SUNDAY, day = "Sat", isSelected = false))
        }

    fun onRepeatItemClick(newItem: AlarmDaysItem) {
        alarmDaysItem.update {
            it.map { oldItem ->
                if (oldItem == newItem) oldItem.copy(isSelected = !oldItem.isSelected)
                else oldItem
            }
        }
    }


}