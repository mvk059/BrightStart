package fyi.manpreet.brightstart.ui.addalarm.components.vibrate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_vibrate
import brightstart.composeapp.generated.resources.common_no
import brightstart.composeapp.generated.resources.common_yes
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource

// TODO Set color of Yes to a reduced alpha of white and not grey
// TODO Maybe keep the icon color as same. Check after adding all other components
@Composable
fun Vibrate(
    modifier: Modifier = Modifier,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
) {

    var isVibrationOn by remember { mutableStateOf(false) }
    val icon = if (isVibrationOn) VibrateOnIcon else VibrateOffIcon
    val statusText =
        if (isVibrationOn) stringResource(Res.string.common_yes)
        else stringResource(Res.string.common_no)
    val tint = if (isVibrationOn) Color.White else Color.Gray

    VibrateContent(
        modifier = modifier.clickable {
            isVibrationOn = !isVibrationOn
            onVibrateUpdate(AddAlarmEvent.VibrateUpdate(isVibrationOn))
        },
        icon = icon,
        statusText = statusText,
        tint = tint,
    )
}

@Composable
private fun VibrateContent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    statusText: String,
    tint: Color,
) {
    Box(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                modifier = Modifier.padding(top = 4.dp),
                imageVector = icon,
                contentDescription = null,
                tint = tint,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(Res.string.add_alarm_vibrate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = statusText,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp
                ),
                color = Color.LightGray,
            )
        }
    }
}
