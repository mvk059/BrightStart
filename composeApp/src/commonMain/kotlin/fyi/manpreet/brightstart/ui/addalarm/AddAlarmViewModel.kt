package fyi.manpreet.brightstart.ui.addalarm

import androidx.lifecycle.ViewModel
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays
import fyi.manpreet.brightstart.ui.model.AlarmDaysItem
import fyi.manpreet.brightstart.ui.model.DaysEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AddAlarmViewModel : ViewModel() {

    val currentAlarm: StateFlow<Alarm?>
        field = MutableStateFlow(null)

    val alarmDays: StateFlow<AlarmDays>
        field = MutableStateFlow(AlarmDays())

    val alarmDaysItem: StateFlow<List<AlarmDaysItem>>
        field = MutableStateFlow(initAlarmDays())

    val repeatDays: StateFlow<String>
        field = MutableStateFlow("")

    fun onEvent(event: AddAlarmEvent) {
        when (event) {
            AddAlarmEvent.AddAlarm -> ::addAlarm
            is AddAlarmEvent.SoundUpdate -> ::onSoundUpdate
            is AddAlarmEvent.VolumeUpdate -> ::onVolumeUpdate
            is AddAlarmEvent.VibrateUpdate -> ::onVibrateUpdate
            is AddAlarmEvent.NameUpdate -> ::onAlarmNameUpdate
            is AddAlarmEvent.RepeatUpdate -> ::onRepeatItemClick
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

    private fun addAlarm() {

    }

    private fun onSoundUpdate(data: Pair<String?, String?>) {
        val ringtoneReference = data.first
        val ringtoneName = data.second
        requireNotNull(ringtoneReference) { "Ringtone Reference is null" }
        requireNotNull(ringtoneName) { "Ringtone Name is null" }

        currentAlarm.update {
            it?.copy(
                ringtoneReference = ringtoneReference,
                ringtoneName = ringtoneName
            )
        }
    }

    private fun onVolumeUpdate(volume: Int) {
        currentAlarm.update { it?.copy(volume = volume) }
    }

    private fun onVibrateUpdate(vibrate: Boolean) {
        currentAlarm.update { it?.copy(vibrationStatus = vibrate) }
    }

    private fun onAlarmNameUpdate(name: String) {
        currentAlarm.update { it?.copy(name = name) }
    }

    private fun onRepeatItemClick(newItem: AlarmDaysItem) {
        alarmDaysItem.update {
            it.map { oldItem ->
                if (oldItem == newItem) oldItem.copy(isSelected = !oldItem.isSelected)
                else oldItem
            }
        }
    }
}