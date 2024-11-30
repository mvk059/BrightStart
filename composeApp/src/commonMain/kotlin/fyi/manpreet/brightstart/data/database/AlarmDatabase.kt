package fyi.manpreet.brightstart.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [AlarmTable::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AlarmDatabaseCtor::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}

expect object AlarmDatabaseCtor : RoomDatabaseConstructor<AlarmDatabase> {
    override fun initialize(): AlarmDatabase
}
