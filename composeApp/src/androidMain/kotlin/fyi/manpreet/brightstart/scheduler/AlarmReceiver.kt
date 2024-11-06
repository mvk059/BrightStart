package fyi.manpreet.brightstart.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    // Inject things here

    override fun onReceive(context: Context?, intent: Intent?) {
        // Callback when alarm is triggered
        val message = intent?.getStringExtra("ALARM_NAME") ?: return
        println("Alarm triggered: $message")
    }

}