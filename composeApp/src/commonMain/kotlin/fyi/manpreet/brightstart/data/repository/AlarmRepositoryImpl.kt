package fyi.manpreet.brightstart.data.repository

import fyi.manpreet.brightstart.data.datasource.AlarmLocalDataSource
import fyi.manpreet.brightstart.data.mapper.toAlarm
import fyi.manpreet.brightstart.data.mapper.toAlarmDays
import fyi.manpreet.brightstart.data.mapper.toAlarmDaysTable
import fyi.manpreet.brightstart.data.mapper.toAlarmTable
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmDays

class AlarmRepositoryImpl(
    private val localDataSource: AlarmLocalDataSource,
) : AlarmRepository {

    override suspend fun insertAlarm(alarm: Alarm, days: AlarmDays): Long {
        val alarmId = localDataSource.insertAlarm(alarm.toAlarmTable())
        val alarmDaysId = insertAlarmDays(alarmId, days)
        return alarmId
    }

    override suspend fun insertAlarmDays(alarmId: Long, alarmDays: AlarmDays): Long =
        localDataSource.insertAlarmDays(alarmDays.toAlarmDaysTable(alarmId = alarmId))

    // TODO Error handling
    override suspend fun fetchAllAlarms(): List<Alarm> {
        val allAlarms = localDataSource.fetchAllAlarms()
        return allAlarms.map { alarm ->
            val alarmDays = fetchAlarmDays(alarm.id)
            alarm.toAlarm(alarmDays ?: AlarmDays()) //.copy(alarmDays = alarmDays ?: AlarmDays())
        }
    }

    override suspend fun fetchAlarmDays(alarmId: Long): AlarmDays? =
        localDataSource.fetchAlarmDays(alarmId)?.toAlarmDays()

    override suspend fun fetchAlarmById(id: Long): Alarm? {
        val alarm = localDataSource.fetchAlarmById(id) //?.toAlarm(alarmDays)
        val alarmDays = localDataSource.fetchAlarmDays(alarm!!.id) // TODO Handle error
        return alarm.toAlarm(alarmDays!!.toAlarmDays())
    }

    override suspend fun deleteAlarm(alarm: Alarm) =
        localDataSource.deleteAlarm(alarm.toAlarmTable())

    override suspend fun updateAlarm(alarm: Alarm) =
        localDataSource.updateAlarm(alarm.toAlarmTable())
}
