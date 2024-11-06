package fyi.manpreet.brightstart.ui.components.sound

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_sound
import fyi.manpreet.brightstart.platform.RingtonePicker
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun Sound(
    modifier: Modifier = Modifier,
    onSoundUpdate: (AddAlarmEvent) -> Unit,
) {
     // TODO Get & Add default sound
    val ringtonePicker: RingtonePicker = koinInject()

    var selectedSoundPath by remember { mutableStateOf("") }
    var selectedSound by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        ringtonePicker.getDefaultRingtone {
            selectedSoundPath = it.first ?: ""
            selectedSound = it.second ?: ""
            if (it.first.isNullOrEmpty().not() && it.second.isNullOrEmpty().not())
                onSoundUpdate(AddAlarmEvent.SoundUpdate(it))
        }
    }

    SoundContent(
        modifier = modifier,
        ringtonePicker = ringtonePicker,
        selectedSound = selectedSound,
        onSoundSelected = {
            if (it.first.isNullOrEmpty().not()) selectedSoundPath = it.first ?: ""
            if (it.second.isNullOrEmpty().not()) selectedSound = it.second ?: ""
            if (it.first.isNullOrEmpty().not() && it.second.isNullOrEmpty().not())
                onSoundUpdate(AddAlarmEvent.SoundUpdate(it))
            // TODO Refactor Code something better
        },
    )
}

@Composable
private fun SoundContent(
    modifier: Modifier = Modifier,
    ringtonePicker: RingtonePicker,
    selectedSound: String,
    onSoundSelected: (Pair<String?, String?>) -> Unit = {},
) {

    println("SoundContent called")
    Box(
        modifier = modifier.wrapContentSize().clickable {
            ringtonePicker.openRingtonePicker {
                println("Ringtone in callback1: $it")
                onSoundSelected(it)
            }
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                imageVector = SoundIcon,
                contentDescription = null,
                tint = Color.White,
            )

            Text(
                text = stringResource(Res.string.add_alarm_sound),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = selectedSound,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray,
            )

        }

    }
}

@Composable
@Preview
fun VibratePreview() {
    MaterialTheme {

//        Sound()
    }
}
