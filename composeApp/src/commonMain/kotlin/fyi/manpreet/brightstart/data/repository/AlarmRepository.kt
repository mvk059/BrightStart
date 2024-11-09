package fyi.manpreet.brightstart.data.repository

import fyi.manpreet.brightstart.data.model.Alarm

interface AlarmRepository {

    suspend fun insertAlarm(alarm: Alarm): Long

    suspend fun fetchAllAlarms(): List<Alarm>

    suspend fun fetchAlarmById(id: Long): Alarm?

    suspend fun deleteAlarm(alarm: Alarm)

    suspend fun updateAlarm(alarm: Alarm)
}