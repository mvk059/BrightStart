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
import fyi.manpreet.brightstart.ui.components.button.CheckIcon
import fyi.manpreet.brightstart.ui.components.button.CloseIcon
import fyi.manpreet.brightstart.ui.components.button.RoundButton
import fyi.manpreet.brightstart.ui.components.sound.Sound
import fyi.manpreet.brightstart.ui.components.vibrate.Vibrate
import fyi.manpreet.brightstart.ui.components.volume.Volume
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 *
 * Time Picker: Takes 70% of the screen
 * TODO Sound, Volume, Vibrate, Repeat, Name, Time left for alarm
 * TODO Close and Save button at bottom left and right respectively
 * TODO Handle status bar and navigation bar background colors
 * TODO Set fonts
 * TODO Setup theme
 */
@Composable
fun AddAlarm(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1E1E26))
    ) {

        Box(
            modifier = Modifier
                .weight(weight = 0.7f)
                .fillMaxWidth()
        ) {

        }

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
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
                    )
                    Volume(
                        modifier = Modifier.weight(0.33f),
                    )
                    Vibrate(
                        modifier = Modifier.weight(0.33f),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Repeat")
                    Text("Name")
                    Text("Time left")
                }

            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    onClick = {}
                )
            }

            Text(text = "Time left for alarm here") // TODO

            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                RoundButton(
                    icon = CheckIcon,
                    onClick = {}
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