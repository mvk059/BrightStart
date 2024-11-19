package fyi.manpreet.brightstart.data.repository

import fyi.manpreet.brightstart.data.datasource.AlarmLocalDataSource
import fyi.manpreet.brightstart.data.mapper.toAlarm
import fyi.manpreet.brightstart.data.mapper.toAlarmTable
import fyi.manpreet.brightstart.data.model.Alarm

class AlarmRepositoryImpl(
    private val localDataSource: AlarmLocalDataSource,
) : AlarmRepository {

    override suspend fun insertAlarm(alarm: Alarm): Long =
        localDataSource.insertAlarm(alarm.toAlarmTable())

    override suspend fun fetchAllAlarms(): List<Alarm> =
        localDataSource.fetchAllAlarms().toAlarm()

    override suspend fun fetchAlarmById(id: Long): Alarm? {
        val alarmWithDays = localDataSource.fetchAlarmById(id)
        return alarmWithDays?.toAlarm()
    }

    override suspend fun deleteAlarm(alarm: Alarm) =
        localDataSource.deleteAlarm(alarm.toAlarmTable())

    override suspend fun updateAlarm(alarm: Alarm) =
        localDataSource.updateAlarm(alarm.toAlarmTable())
}
