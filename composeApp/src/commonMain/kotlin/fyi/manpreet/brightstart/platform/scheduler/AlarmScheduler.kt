package fyi.manpreet.brightstart.platform.scheduler

import fyi.manpreet.brightstart.data.model.Alarm

interface AlarmScheduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}

expect class AlarmSchedulerImpl : AlarmScheduler {
    override fun schedule(alarm: Alarm)
    override fun cancel(alarm: Alarm)
}