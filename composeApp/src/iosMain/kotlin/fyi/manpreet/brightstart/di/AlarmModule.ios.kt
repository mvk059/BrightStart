package fyi.manpreet.brightstart.di

import fyi.manpreet.brightstart.platform.scheduler.AlarmScheduler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import fyi.manpreet.brightstart.platform.scheduler.AlarmSchedulerImpl

actual fun provideAlarmModule(): Module = module {
    singleOf(::AlarmSchedulerImpl).bind(AlarmScheduler::class)
}
