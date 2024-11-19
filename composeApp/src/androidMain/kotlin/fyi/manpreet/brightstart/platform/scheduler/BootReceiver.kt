package fyi.manpreet.brightstart.platform.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class BootReceiver : BroadcastReceiver(), KoinComponent {

    private val alarmInteraction: AlarmInteraction by inject(AlarmInteraction::class.java)
    private val alarmScheduler: AlarmScheduler by inject(AlarmScheduler::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED || intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            println("BootReceiver: Boot completed")
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val alarms = alarmInteraction.getAllAlarms()
                alarms.forEach { alarm ->
                    println("Rescheduling alarm: $alarm")
                    alarmScheduler.schedule(alarm)
                }
            }
        } else {
            println("BootReceiver: Boot not completed")
        }
    }
}