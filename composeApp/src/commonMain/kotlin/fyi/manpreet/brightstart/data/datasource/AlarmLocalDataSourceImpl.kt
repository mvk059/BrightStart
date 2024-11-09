package fyi.manpreet.brightstart.data.datasource

import fyi.manpreet.brightstart.data.database.AlarmDatabase
import fyi.manpreet.brightstart.data.database.AlarmTable

class AlarmLocalDataSourceImpl(
    private val db: AlarmDatabase,
) : AlarmLocalDataSource {

    override suspend fun insertAlarm(alarm: AlarmTable): Long =
        db.alarmDao().insertAlarm(alarm = alarm)

    override suspend fun fetchAllAlarms(): List<AlarmTable> = db.alarmDao().getAllAlarms()

    override suspend fun fetchAlarmById(alarmId: Long): AlarmTable? =
        db.alarmDao().getAlarmById(alarmId = alarmId)

    override suspend fun deleteAlarm(alarm: AlarmTable) = db.alarmDao().deleteAlarm(alarm = alarm)


    override suspend fun updateAlarm(alarm: AlarmTable) = db.alarmDao().updateAlarm(alarm = alarm)

}