package fyi.manpreet.brightstart.ui.components.volume

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_volume
import com.composables.core.Menu
import com.composables.core.MenuButton
import com.composables.core.MenuContent
import com.composables.core.MenuItem
import com.composables.core.rememberMenuState
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Volume(
    modifier: Modifier = Modifier,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
) {

    val volumeSelectionState = rememberMenuState(expanded = false)

    var volumeValue by remember { mutableStateOf(100) }
    val icon by remember { derivedStateOf { if (volumeValue > 1) VolumeOnIcon else VolumeOffIcon } }
    val tint by remember { derivedStateOf { if (volumeValue > 1) Color.White else Color.Gray } }

    Box(
        modifier = modifier
    ) {
        Menu(
            state = volumeSelectionState,
            modifier = Modifier.align(Alignment.Center),
        ) {
            MenuButton {
                VolumeContent(
//                modifier = modifier,
                    icon = icon,
                    volumeValue = volumeValue,
                    tint = tint,
                    onVolumeClick = { volumeSelectionState.expanded = true }
                )
            }

            // TODO play around with alignment and animation
            // Add animated counter as well https://www.sinasamaki.com/animated-counter/
            MenuContent(
                alignment = Alignment.End,
                enter = scaleIn(
                    tween(durationMillis = 120, easing = LinearOutSlowInEasing),
                    initialScale = 0.8f,
                    transformOrigin = TransformOrigin(0f, 0f)
                ) + fadeIn(tween(durationMillis = 30)),
                exit = scaleOut(
                    tween(durationMillis = 1, delayMillis = 75),
                    targetScale = 1f
                ) + fadeOut(tween(durationMillis = 75)),
            ) {
                MenuItem(onClick = { volumeSelectionState.expanded = false }) {
                    StretchySlider(
                        defaultValue = volumeValue.toFloat(),
                        onValueChange = {
                            volumeValue = it
                            onVolumeUpdate(AddAlarmEvent.VolumeUpdate(it))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun VolumeContent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    volumeValue: Int,
    tint: Color,
    onVolumeClick: () -> Unit,
) {

    Box(modifier = modifier) {

        Column(
            modifier = Modifier.align(Alignment.Center).clickable { onVolumeClick() },
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
                text = stringResource(Res.string.add_alarm_volume),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )

            Text(
                text = volumeValue.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray,
            )
        }
    }
}
