package fyi.manpreet.brightstart.data.repository

import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays

interface AlarmRepository {

    suspend fun insertAlarm(alarm: Alarm, days: AlarmDays): Long

    suspend fun insertAlarmDays(alarmId: Long, alarmDays: AlarmDays): Long

    suspend fun fetchAllAlarms(): List<Alarm>

    suspend fun fetchAlarmDays(alarmId: Long): AlarmDays?

    suspend fun fetchAlarmById(id: Long): Alarm?

    suspend fun deleteAlarm(alarm: Alarm)

    suspend fun updateAlarm(alarm: Alarm)
}