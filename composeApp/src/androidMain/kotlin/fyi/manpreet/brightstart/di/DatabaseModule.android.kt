package fyi.manpreet.brightstart.di

import fyi.manpreet.brightstart.data.database.AlarmDatabase
import fyi.manpreet.brightstart.data.database.getAlarmDatabase
import org.koin.dsl.module

actual fun provideDatabaseModule() = module {
    single<AlarmDatabase> { getAlarmDatabase(get()) }
}