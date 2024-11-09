package fyi.manpreet.brightstart.ui.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.components.button.CheckIcon
import fyi.manpreet.brightstart.ui.components.button.CloseIcon
import fyi.manpreet.brightstart.ui.components.button.RoundButton
import fyi.manpreet.brightstart.ui.components.clock.Clock
import fyi.manpreet.brightstart.ui.components.name.Name
import fyi.manpreet.brightstart.ui.components.repeat.Repeat
import fyi.manpreet.brightstart.ui.components.sound.Sound
import fyi.manpreet.brightstart.ui.components.vibrate.Vibrate
import fyi.manpreet.brightstart.ui.components.volume.Volume
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

    // TODO Adjust weight accordingly
    println("AddAlarmScreen: ${alarm.value}")

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
                Text(
                    text = "Left 35%",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
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

            MiddleRow(
//                modifier = Modifier.weight(0.3f),
                onVolumeUpdate = onVolumeUpdate,
                onVibrateUpdate = onVibrateUpdate,
                onNameUpdate = onNameUpdate,
                alarm = alarm,
                repeatDays = repeatDays,
                onRepeatUpdate = onRepeatUpdate,
                openRingtonePicker = openRingtonePicker,
            )

            BottomRow(
                onAddClick = onAddClick,
                onCloseClick = onCloseClick,
            )

//            // First row
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//                    .background(color = Color.Red)
//            ) {
//                Text(
//                    text = "Row 1",
//                    modifier = Modifier.align(Alignment.Center),
//                    color = Color.White
//                )
//            }
//
//            // Second row
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//                    .background(color = Color.Green)
//            ) {
//                Text(
//                    text = "Row 2",
//                    modifier = Modifier.align(Alignment.Center),
//                    color = Color.White
//                )
//            }
//
//            // Third row
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//                    .background(color = Color.Blue)
//            ) {
//                Text(
//                    text = "Row 3",
//                    modifier = Modifier.align(Alignment.Center),
//                    color = Color.White
//                )
//            }
        }


    }
}

@Composable
fun TimeSelection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Clock(
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MiddleRow(
    modifier: Modifier = Modifier,
    alarm: StateFlow<Alarm>,
    repeatDays: StateFlow<String>,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
    openRingtonePicker: () -> Unit,
) {

    val alarm = alarm.collectAsStateWithLifecycle()
    val repeatDays = repeatDays.collectAsStateWithLifecycle()

    println("Current Alarm MiddleRow: ${alarm.value}")

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Sound(
                    modifier = Modifier.weight(0.34f),
                    alarmName = alarm.value.ringtoneName,
                    openRingtonePicker = openRingtonePicker,
                )
                Volume(
                    modifier = Modifier.weight(0.33f),
                    onVolumeUpdate = onVolumeUpdate,
                )
                Vibrate(
                    modifier = Modifier.weight(0.33f),
                    onVibrateUpdate = onVibrateUpdate,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Name(
//                        modifier = Modifier.weight(0.5f), TODO Check
                    onNameUpdate = onNameUpdate,
                )
                Repeat(
//                        modifier = Modifier.weight(0.5f),
                    alarmDays = alarm.value.alarmDays,
                    repeatDays = repeatDays.value,
                    onRepeatUpdate = onRepeatUpdate,
                )
            }

        }
    }
}

@Composable
private fun BottomRow(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        //TODO Maybe take the weight for this row and set size of button based on that
        // Bottom buttons
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            RoundButton(
                icon = CloseIcon,
                onClick = onCloseClick
            )
        }

        Text(text = "Time left for alarm here") // TODO

        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            RoundButton(
                icon = CheckIcon,
                onClick = onAddClick
            )
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