package fyi.manpreet.brightstart.scheduler

import fyi.manpreet.brightstart.ui.model.AlarmItem

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}

expect class AlarmSchedulerImpl : AlarmScheduler {
    override fun schedule(alarmItem: AlarmItem)
    override fun cancel(alarmItem: AlarmItem)
}