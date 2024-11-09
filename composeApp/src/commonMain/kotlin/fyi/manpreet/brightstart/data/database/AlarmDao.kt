package fyi.manpreet.brightstart.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmDao {

    @Insert
    suspend fun insertAlarm(alarm: AlarmTable): Long

    @Update
    suspend fun updateAlarm(alarm: AlarmTable)

    @Delete
    suspend fun deleteAlarm(alarm: AlarmTable)

    @Query("SELECT * FROM alarm_table")
    suspend fun getAllAlarms(): List<AlarmTable>

    @Query("SELECT * FROM alarm_table WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: Long): AlarmTable?

    @Query("SELECT * FROM alarm_table WHERE is_active = 1") // TODO Check if 1 works as boolean
    suspend fun getActiveAlarms(): List<AlarmTable>

    @Query("UPDATE alarm_table SET is_active = :isActive WHERE id = :alarmId")
    suspend fun updateAlarmStatus(alarmId: Long, isActive: Boolean)

}
