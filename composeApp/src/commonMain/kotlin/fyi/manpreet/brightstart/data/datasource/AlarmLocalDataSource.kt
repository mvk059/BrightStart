package fyi.manpreet.brightstart.data.datasource

import fyi.manpreet.brightstart.data.database.AlarmTable

interface AlarmLocalDataSource {

    suspend fun insertAlarm(alarm: AlarmTable): Long

    suspend fun fetchAllAlarms(): List<AlarmTable>

    suspend fun fetchAlarmById(alarmId: Long): AlarmTable?

    suspend fun deleteAlarm(alarm: AlarmTable)

    suspend fun updateAlarm(alarm: AlarmTable)

}
