package fyi.manpreet.brightstart.data.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import fyi.manpreet.brightstart.data.database.AlarmDatabase
import fyi.manpreet.brightstart.di.DatabaseConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

fun getAlarmDatabase(): AlarmDatabase {
    val dbFile = NSHomeDirectory() + DatabaseConstants.SLASH + DatabaseConstants.DB_NAME
    return Room.databaseBuilder<AlarmDatabase>(
        name = dbFile,
        factory = { AlarmDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
