package fyi.manpreet.brightstart

import android.app.Application
import fyi.manpreet.brightstart.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BrightStartApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@BrightStartApp)
        }
    }
}