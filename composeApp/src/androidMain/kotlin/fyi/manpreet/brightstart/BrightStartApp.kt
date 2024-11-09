package fyi.manpreet.brightstart

import android.app.Application
import fyi.manpreet.brightstart.di.initKoin

class BrightStartApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}