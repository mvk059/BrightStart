package fyi.manpreet.brightstart.platform.scheduler

import fyi.manpreet.brightstart.data.model.Alarm

interface AlarmInteraction {
    fun onAlarmDismiss(id: Long)
    fun onAlarmSnooze(id: Long)
    suspend fun getAlarm(id: Long): Alarm?
    suspend fun getAllAlarms(): List<Alarm>
}