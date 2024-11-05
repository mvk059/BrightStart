package fyi.manpreet.brightstart.ui.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 *
 * Time Picker: Takes 70% of the screen
 * Sound, Volume, Vibrate, Repeat, Name, Time left for alarm
 * Close and Save button at bottom left and right respectively
 * Handle status bar and navigation bar background colors
 */
@Composable
fun AddAlarm(
    modifier: Modifier = Modifier
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
                .background(color = Color.Red)
        ) {

        }

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
                .background(color = Color.Green)
        ) {

            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Sound")
                    Text("Volume")
                    Text("Vibrate")
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


            // Bottom buttons
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Button(
                    onClick = { /* TODO: Handle left button click */ },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(56.dp)
                        .background(Color.Red, CircleShape)
                ) {
                    Text("Left")
                }
            }

            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = { /* TODO: Handle right button click */ },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(56.dp)
                        .background(Color.Red, CircleShape)
                ) {
                    Text("Right")
                }
            }
        }
    }
}

@Preview
@Composable
fun AddAlarmPreview() {
    MaterialTheme {
        AddAlarm()
    }

}