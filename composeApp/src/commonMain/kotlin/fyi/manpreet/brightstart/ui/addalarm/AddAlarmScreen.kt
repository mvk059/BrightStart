package fyi.manpreet.brightstart.ui.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.addalarm.items.AddAlarmClockOne
import fyi.manpreet.brightstart.ui.addalarm.items.AddAlarmRowOne
import fyi.manpreet.brightstart.ui.addalarm.items.AddAlarmRowThree
import fyi.manpreet.brightstart.ui.addalarm.items.AddAlarmRowTwo
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 *
 * Time Picker: Takes 70% of the screen
 * TODO Sound, Volume, Vibrate, Repeat, Name, Time left for alarm
 * TODO Close and Save button at bottom left and right respectively
 * TODO Handle status bar and navigation bar background colors
 * TODO Set fonts
 * TODO Setup theme
 * TODO Set text size/style based on theme for all text
 * TODO Keep weight of both the rows same in terms of height as well
 */
@Composable
fun AddAlarm(
    modifier: Modifier = Modifier,
    alarm: StateFlow<Alarm>,
    repeatDays: StateFlow<String>,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
    openRingtonePicker: () -> Unit,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    // TODO Add scaffold for app bar?
    // TODO Adjust weight accordingly
    // TODO Increase padding of each component slightly for better clickability
    println("AddAlarmScreen: ${alarm.value}")
    val alarm = alarm.collectAsStateWithLifecycle()
    val repeatDays = repeatDays.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1E1E26))
    ) {

        // 70% of the screen
        Row(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            // Left half of the 70% section
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Color.Gray)
            ) {
                AddAlarmClockOne()
            }

            // Right half of the 70% section
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Color.LightGray)
            ) {
                Text(
                    text = "Right 35%",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black
                )
            }
        }

        // 30% of the screen
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        ) {

            // First row
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = Color.Red)
            ) {
                AddAlarmRowOne(
                    alarm = alarm.value,
                    openRingtonePicker = openRingtonePicker,
                    onVolumeUpdate = onVolumeUpdate,
                    onVibrateUpdate = onVibrateUpdate,
                )
            }

            // Second row
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = Color.Green)
            ) {
                AddAlarmRowTwo(
                    alarm = alarm.value,
                    repeatDays = repeatDays.value,
                    onNameUpdate = onNameUpdate,
                    onRepeatUpdate = onRepeatUpdate,
                )
            }

            // Third row
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = Color.Blue)
            ) {
                AddAlarmRowThree(
                    onAddClick = onAddClick,
                    onCloseClick = onCloseClick,
                )
            }
        }


    }
}

@Preview
@Composable
fun AddAlarmPreview() {
    MaterialTheme {
//        AddAlarm()
    }

}