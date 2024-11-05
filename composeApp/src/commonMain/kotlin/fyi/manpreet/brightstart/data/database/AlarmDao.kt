package fyi.manpreet.brightstart.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmDao {

    // Insert a new alarm
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmTable): Long

    // Insert repetition days for an alarm TODO Check if this can be removed as data is pre-populated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmDays(alarmDays: AlarmDaysTable): Long

    // Retrieve all alarms TODO Check if return type needs to be a flow
    @Query("SELECT * FROM alarm_table")
    suspend fun getAllAlarms(): List<AlarmTable>

    // Retrieve an alarm by ID
    @Query("SELECT * FROM alarm_table WHERE id = :id")
    suspend fun getAlarmById(id: Long): AlarmTable?

    // Retrieve alarm days by alarm ID
    @Query("SELECT * FROM alarm_table_days WHERE alarm_id = :alarmId")
    suspend fun getAlarmDays(alarmId: Long): AlarmDaysTable?

    // Delete an alarm (and associated AlarmDays due to cascade) TODO Check if delete by id is needed
    @Delete
    suspend fun deleteAlarm(alarm: AlarmTable)

    // Update an existing alarm
    @Update
    suspend fun updateAlarm(alarm: AlarmTable)
}