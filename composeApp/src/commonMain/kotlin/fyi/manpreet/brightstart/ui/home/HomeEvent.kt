package fyi.manpreet.brightstart.ui.home

import fyi.manpreet.brightstart.data.model.Alarm

sealed interface HomeEvent {

    data object FetchAlarms : HomeEvent

    data class DeleteAlarm(val alarm: Alarm) : HomeEvent

    data class ToggleAlarm(val alarm: Alarm, val status: Boolean) : HomeEvent
}
