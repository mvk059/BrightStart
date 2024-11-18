package fyi.manpreet.brightstart

import android.app.Activity
import android.app.KeyguardManager
import android.app.KeyguardManager.KeyguardDismissCallback
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants
import fyi.manpreet.brightstart.platform.scheduler.AlarmInteraction
import org.koin.java.KoinJavaComponent
import kotlin.getValue

// TODO Decide what to do on onResume, onStop
class AlarmTriggerActivity : ComponentActivity() {

    private val alarmInteraction: AlarmInteraction by KoinJavaComponent.inject(AlarmInteraction::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure the screen is turned on
        turnScreenOnAndKeyguardOff()
        val notificationManager = getSystemService(NotificationManager::class.java)

        setContent {

            val alarmState = remember { mutableStateOf<Alarm?>(null) }
            val id = intent.getIntExtra(AlarmConstants.ALARM_ID, -1).toLong()
            LaunchedEffect(true) {
                alarmState.value = alarmInteraction.getAlarm(id)
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Center,
            ) {

                Text(
                    text = "${alarmState.value?.time?.value}",
                )

                Text(
                    text = "${alarmState.value?.name?.value}"
                )

                Button(
                    onClick = {
                        alarmInteraction.onAlarmDismiss(id)
                        notificationManager.cancel(id.toInt())
                        finish()
                    },
                    content = {
                        Text("Turn Off")
                    }
                )

                Button(
                    onClick = {
                        alarmInteraction.onAlarmSnooze(id)
                        notificationManager.cancel(id.toInt())
                        finish()
                    },
                    content = {
                        Text("Snooze for 5 minutes")
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }

    fun Activity.turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }

        with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
            requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, object : KeyguardDismissCallback() {
                override fun onDismissError() {
                    super.onDismissError()
                    println("onDismissError")
                }

                override fun onDismissSucceeded() {
                    super.onDismissSucceeded()
                    println("onDismissSucceeded")
                }

                override fun onDismissCancelled() {
                    super.onDismissCancelled()
                    println("onDismissCancelled")
                }
            })
        }
    }

    fun Activity.turnScreenOffAndKeyguardOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false)
            setTurnScreenOn(false)
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
    }

}
