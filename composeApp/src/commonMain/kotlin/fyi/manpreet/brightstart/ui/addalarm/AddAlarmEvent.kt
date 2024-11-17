package fyi.manpreet.brightstart.ui.addalarm

import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.platform.permission.Permission

sealed interface AddAlarmEvent {

    data object AddAlarm : AddAlarmEvent

    data class SoundUpdate(val data: Pair<String?, String?>) : AddAlarmEvent

    data class VolumeUpdate(val volume: Int) : AddAlarmEvent

    data class VibrateUpdate(val vibrationStatus: Boolean) : AddAlarmEvent

    data class NameUpdate(val name: String) : AddAlarmEvent

    data class RepeatUpdate(val item: AlarmDays) : AddAlarmEvent

    data class OpenSettings(val type: Permission) : AddAlarmEvent

    data object DismissPermissionDialog : AddAlarmEvent

}
