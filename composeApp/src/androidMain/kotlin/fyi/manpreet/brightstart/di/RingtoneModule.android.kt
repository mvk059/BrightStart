package fyi.manpreet.brightstart.di

import android.app.Activity
import fyi.manpreet.brightstart.platform.RingtonePicker
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun provideRingtoneModule(): Module = module {
    single<RingtonePicker> {
        RingtonePicker(androidContext() as Activity)
    }
}