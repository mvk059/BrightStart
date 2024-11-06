package fyi.manpreet.brightstart.ui.addalarm

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays
import fyi.manpreet.brightstart.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.model.AlarmDaysItem
import fyi.manpreet.brightstart.ui.model.AlarmItem
import fyi.manpreet.brightstart.ui.model.DaysEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

class AddAlarmViewModel(
    private val alarmScheduler: AlarmScheduler,
) : ViewModel() {

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
            AddAlarmEvent.AddAlarm -> addAlarm()
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
        Logger.d("Alarm addAlarm")
        val currentTime =
            Clock.System.now().plus(10.seconds).toLocalDateTime(TimeZone.currentSystemDefault())

        val alarm = Alarm(
            localTime = currentTime,
            time = "",
            name = "New Alarm",
            ringtoneReference = "",
            ringtoneName = "",
            volume = 0,
            vibrationStatus = false,
            alarmDays = AlarmDays(),
            isActive = true,
        )
        val alarmDaysItem = AlarmDaysItem(DaysEnum.MONDAY, "Mon", false)
        val item = AlarmItem(alarm, alarmDaysItem)

        Logger.d("Alarm schedule start")
        alarmScheduler.schedule(item)
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