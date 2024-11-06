package fyi.manpreet.brightstart.ui.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import fyi.manpreet.brightstart.ui.components.button.CheckIcon
import fyi.manpreet.brightstart.ui.components.button.CloseIcon
import fyi.manpreet.brightstart.ui.components.button.RoundButton
import fyi.manpreet.brightstart.ui.components.name.Name
import fyi.manpreet.brightstart.ui.components.repeat.Repeat
import fyi.manpreet.brightstart.ui.components.sound.Sound
import fyi.manpreet.brightstart.ui.components.vibrate.Vibrate
import fyi.manpreet.brightstart.ui.components.volume.Volume
import fyi.manpreet.brightstart.ui.home.HomeEvent
import fyi.manpreet.brightstart.ui.model.AlarmDaysItem
import fyi.manpreet.brightstart.ui.model.AlarmItem
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
    alarmDaysItem: StateFlow<List<AlarmDaysItem>>,
    repeatDays: StateFlow<String>,
    onSoundUpdate: (AddAlarmEvent) -> Unit,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    // TODO Adjust weight accordingly

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1E1E26))
    ) {

        TimeSelection(modifier = Modifier.weight(weight = 0.7f))

        MiddleRow(
            modifier = Modifier.weight(0.3f),
            onSoundUpdate = onSoundUpdate,
            onVolumeUpdate = onVolumeUpdate,
            onVibrateUpdate = onVibrateUpdate,
            onNameUpdate = onNameUpdate,
            alarmDaysItem = alarmDaysItem,
            repeatDays = repeatDays,
            onRepeatUpdate = onRepeatUpdate,
        )

        BottomRow(
            onAddClick = onAddClick,
            onCloseClick = onCloseClick,
        )
    }
}

@Composable
fun TimeSelection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {

    }
}

@Composable
fun MiddleRow(
    modifier: Modifier = Modifier,
    onSoundUpdate: (AddAlarmEvent) -> Unit,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    alarmDaysItem: StateFlow<List<AlarmDaysItem>>,
    repeatDays: StateFlow<String>,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
) {

    val alarmDaysItem = alarmDaysItem.collectAsStateWithLifecycle()
    val repeatDays = repeatDays.collectAsStateWithLifecycle()

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
                    onSoundUpdate = onSoundUpdate,
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
                    alarmDaysItem = alarmDaysItem.value,
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