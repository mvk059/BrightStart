package fyi.manpreet.brightstart.data.datasource

import fyi.manpreet.brightstart.data.database.AlarmDaysTable
import fyi.manpreet.brightstart.data.database.AlarmTable

interface AlarmLocalDataSource {

    suspend fun insertAlarm(alarm: AlarmTable): Long

    suspend fun insertAlarmDays(alarmDays: AlarmDaysTable): Long

    suspend fun fetchAllAlarms(): List<AlarmTable>

    suspend fun fetchAlarmById(id: Long): AlarmTable?

    suspend fun fetchAlarmDays(alarmId: Long): AlarmDaysTable?

    suspend fun deleteAlarm(alarm: AlarmTable)

    suspend fun updateAlarm(alarm: AlarmTable)
}