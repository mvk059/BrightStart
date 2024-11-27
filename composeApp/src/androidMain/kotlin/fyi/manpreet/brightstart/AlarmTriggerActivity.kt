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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.platform.scheduler.AlarmConstants
import fyi.manpreet.brightstart.platform.scheduler.AlarmInteraction
import org.koin.java.KoinJavaComponent

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
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF1E1E26)),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = "${alarmState.value?.time?.value}",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                )

                Text(
                    text = "${alarmState.value?.name?.value}",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White,
                )

                Button(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {
                        alarmInteraction.onAlarmDismiss(id)
                        notificationManager.cancel(id.toInt())
                        finish()
                    },
                    content = {
                        Text(
                            text = "Turn Off",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                        )
                    }
                )

                Button(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {
                        alarmInteraction.onAlarmSnooze(id)
                        notificationManager.cancel(id.toInt())
                        finish()
                    },
                    content = {
                        Text(
                            text = "Snooze for 5 minutes",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                        )
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }

    private fun Activity.turnScreenOnAndKeyguardOff() {
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
            requestDismissKeyguard(
                this@turnScreenOnAndKeyguardOff,
                object : KeyguardDismissCallback() {
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

    private fun Activity.turnScreenOffAndKeyguardOn() {
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
