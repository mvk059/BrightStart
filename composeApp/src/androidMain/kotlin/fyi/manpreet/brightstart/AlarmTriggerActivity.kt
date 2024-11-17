package fyi.manpreet.brightstart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fyi.manpreet.brightstart.platform.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.home.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

// TODO Decide what to do on onResume, onStop
class AlarmTriggerActivity : ComponentActivity() {

    private val sharedViewModel: HomeViewModel by viewModel()
    private val alarmScheduler: AlarmScheduler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val snooze = intent.getBooleanExtra("SNOOZE", false)
            val close = intent.getBooleanExtra("CLOSE", false)
            val dismiss = intent.getBooleanExtra("DISMISS", false)
            val empty = intent.getBooleanExtra("EMPTY", false)
            val id = intent.getLongExtra("ALARM_ID", -1)

            val alarmTriggerState = sharedViewModel.alarmTriggerState.collectAsStateWithLifecycle(this)

            LaunchedEffect(alarmTriggerState.value) {
                println("LaunchedEffectTri Alarm Triggered: ${alarmTriggerState.value}")
                if (alarmTriggerState.value == null)
                    sharedViewModel.onAlarmTrigger(id)
            }

            Text(
                """
                Snooze: $snooze
                Close: $close
                Dismiss: $dismiss
                Empty: $empty
                ID: $id
                """.trimIndent()
            )

            if (alarmTriggerState.value != null) {
                println("Alarm Triggered not null: ${alarmTriggerState.value}")
                alarmScheduler.cancel(alarmTriggerState.value!!)
            }
        }
    }
}