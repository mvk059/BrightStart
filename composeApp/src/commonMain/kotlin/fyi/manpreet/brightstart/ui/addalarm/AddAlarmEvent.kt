package fyi.manpreet.brightstart.ui.addalarm

import fyi.manpreet.brightstart.ui.model.AlarmDaysItem

sealed interface AddAlarmEvent {

    data object AddAlarm : AddAlarmEvent

    data class SoundUpdate(val data: Pair<String?, String?>) : AddAlarmEvent

    data class VolumeUpdate(val volume: Int) : AddAlarmEvent

    data class VibrateUpdate(val vibrationStatus: Boolean) : AddAlarmEvent

    data class NameUpdate(val name: String) : AddAlarmEvent

    data class RepeatUpdate(val item: AlarmDaysItem): AddAlarmEvent

}
