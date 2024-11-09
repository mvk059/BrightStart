package fyi.manpreet.brightstart.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
data class AddAlarmDestination(
    val alarmId: Long? = null,
)