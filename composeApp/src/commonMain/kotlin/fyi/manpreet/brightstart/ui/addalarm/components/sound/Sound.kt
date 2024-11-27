package fyi.manpreet.brightstart.ui.addalarm.components.sound

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_sound
import org.jetbrains.compose.resources.stringResource

@Composable
fun Sound(
    modifier: Modifier = Modifier,
    alarmName: String,
    openRingtonePicker: () -> Unit,
) {

    // TODO Remove side effect
    var selectedSound by remember { mutableStateOf("") }

    LaunchedEffect(alarmName) {
        selectedSound = alarmName
    }

    SoundContent(
        modifier = modifier.clickable { openRingtonePicker() },
        selectedSound = selectedSound,
    )
}

@Composable
private fun SoundContent(
    modifier: Modifier = Modifier,
    selectedSound: String,
) {

    Box(modifier = modifier) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                modifier = Modifier.padding(top = 4.dp),
                imageVector = SoundIcon,
                contentDescription = null,
                tint = Color.White,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(Res.string.add_alarm_sound),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = selectedSound,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp
                ),
                color = Color.LightGray,
                textAlign = TextAlign.Center,
            )
        }
    }
}
