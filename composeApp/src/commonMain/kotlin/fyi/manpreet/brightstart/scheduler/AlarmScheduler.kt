package fyi.manpreet.brightstart.scheduler

import fyi.manpreet.brightstart.data.model.Alarm

interface AlarmScheduler {
    fun schedule(alarmItem: Alarm)
    fun cancel(alarmItem: Alarm)
}

expect class AlarmSchedulerImpl : AlarmScheduler {
    override fun schedule(alarm: Alarm)
    override fun cancel(alarm: Alarm)
}