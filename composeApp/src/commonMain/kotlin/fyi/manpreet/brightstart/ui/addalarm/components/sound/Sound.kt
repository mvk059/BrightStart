package fyi.manpreet.brightstart.ui.addalarm.components.sound

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextOverflow
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_sound
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Sound(
    modifier: Modifier = Modifier,
    alarmName: String,
    openRingtonePicker: () -> Unit,
) {

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


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(Res.string.add_alarm_sound),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = selectedSound,
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
        )
    }
}

@Composable
@Preview
fun SoundPreview() {
    MaterialTheme {

//        Sound()
    }
}
