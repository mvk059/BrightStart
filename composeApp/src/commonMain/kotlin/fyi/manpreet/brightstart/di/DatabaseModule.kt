package fyi.manpreet.brightstart.di

import org.koin.core.module.Module

expect fun provideDatabaseModule(): Module

object DatabaseConstants {
    const val DB_NAME = "brightstart.db"
    const val SLASH = "/"
}