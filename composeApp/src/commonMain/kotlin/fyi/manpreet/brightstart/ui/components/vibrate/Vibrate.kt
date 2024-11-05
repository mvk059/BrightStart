package fyi.manpreet.brightstart.ui.components.vibrate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_vibrate
import brightstart.composeapp.generated.resources.common_no
import brightstart.composeapp.generated.resources.common_yes
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// TODO Set color of Yes to a reduced alpha of white and not grey
// TODO Maybe keep the icon color as same. Check after adding all other components
@Composable
fun Vibrate(
    modifier: Modifier = Modifier
) {

    var isVibrationOn by remember { mutableStateOf(false) }
    val icon = if (isVibrationOn) VibrateOnIcon else VibrateOffIcon
    val statusText =
        if (isVibrationOn) stringResource(Res.string.common_yes)
        else stringResource(Res.string.common_no)
    val tint = if (isVibrationOn) Color.White else Color.Gray

    VibrateContent(
        modifier = modifier,
        icon = icon,
        statusText = statusText,
        tint = tint,
        onVibrationToggle = { isVibrationOn = !isVibrationOn }
    )
}

@Composable
private fun VibrateContent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    statusText: String,
    tint: Color,
    onVibrationToggle: () -> Unit,
) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {

        Column(
            modifier = Modifier.clickable { onVibrationToggle() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
            )

            Text(
                text = stringResource(Res.string.add_alarm_vibrate),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )

            Text(
                text = statusText,
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
        Vibrate()
    }
}
