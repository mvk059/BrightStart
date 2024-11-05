package fyi.manpreet.brightstart.ui.home

sealed interface HomeEvent {

    data object FetchAlarms: HomeEvent

    data object FetchAlarmDays: HomeEvent

    data object AddAlarm: HomeEvent

}
