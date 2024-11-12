package fyi.manpreet.brightstart.ui.addalarm.components.vibrate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_vibrate
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// TODO Set color of Yes to a reduced alpha of white and not grey
// TODO Maybe keep the icon color as same. Check after adding all other components
@Composable
fun Vibrate(
    modifier: Modifier = Modifier,
    vibrationStatus: Boolean,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
) {

    var isVibrationOn by remember { mutableStateOf(vibrationStatus) }

    VibrateContent(
        modifier = modifier,
        isVibrationOn = isVibrationOn,
        onVibrationToggle = {
            isVibrationOn = !isVibrationOn
            onVibrateUpdate(AddAlarmEvent.VibrateUpdate(isVibrationOn))
        }
    )
}

@Composable
private fun VibrateContent(
    modifier: Modifier = Modifier,
    isVibrationOn: Boolean,
    onVibrationToggle: () -> Unit,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(Res.string.add_alarm_vibrate),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Switch(
            checked = isVibrationOn,
            onCheckedChange = { onVibrationToggle() },
        )

    }
}

@Composable
@Preview
fun VibratePreview() {
    MaterialTheme {
        Vibrate(vibrationStatus = true, onVibrateUpdate = {})
    }
}
