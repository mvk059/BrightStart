package fyi.manpreet.brightstart.data.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import fyi.manpreet.brightstart.di.DatabaseConstants
import kotlinx.coroutines.Dispatchers

fun getAlarmDatabase(context: Context): AlarmDatabase {
    val dbFile = context.getDatabasePath(DatabaseConstants.DB_NAME)
    return Room.databaseBuilder<AlarmDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
