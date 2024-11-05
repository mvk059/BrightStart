package fyi.manpreet.brightstart.data.datasource

import fyi.manpreet.brightstart.data.database.AlarmDatabase
import fyi.manpreet.brightstart.data.database.AlarmDaysTable
import fyi.manpreet.brightstart.data.database.AlarmTable

class AlarmLocalDataSourceImpl(
    private val db: AlarmDatabase,
) : AlarmLocalDataSource {

    override suspend fun insertAlarm(alarm: AlarmTable): Long = db.alarmDao().insertAlarm(alarm)

    override suspend fun insertAlarmDays(alarmDays: AlarmDaysTable): Long = db.alarmDao().insertAlarmDays(alarmDays)

    override suspend fun fetchAllAlarms(): List<AlarmTable> = db.alarmDao().getAllAlarms()

    override suspend fun fetchAlarmById(id: Long): AlarmTable? = db.alarmDao().getAlarmById(id)

    override suspend fun fetchAlarmDays(alarmId: Long): AlarmDaysTable? =
        db.alarmDao().getAlarmDays(alarmId)

    override suspend fun deleteAlarm(alarm: AlarmTable) = db.alarmDao().deleteAlarm(alarm)

    override suspend fun updateAlarm(alarm: AlarmTable) = db.alarmDao().updateAlarm(alarm)

}