package fyi.manpreet.brightstart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AlarmTable::class, AlarmDaysTable::class],
    version = 1
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
