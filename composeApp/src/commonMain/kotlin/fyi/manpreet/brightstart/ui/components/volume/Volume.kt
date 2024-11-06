package fyi.manpreet.brightstart.ui.components.volume

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_vibrate
import brightstart.composeapp.generated.resources.add_alarm_volume
import brightstart.composeapp.generated.resources.common_no
import brightstart.composeapp.generated.resources.common_yes
import com.composables.core.Menu
import com.composables.core.MenuButton
import com.composables.core.MenuContent
import com.composables.core.MenuItem
import com.composables.core.MenuState
import com.composables.core.rememberMenuState
import kotlinx.datetime.TimeZone.Companion.of
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Volume(
    modifier: Modifier = Modifier,
) {

    val volumeSelectionState = rememberMenuState(expanded = false)

//    var isVolumeOn by remember { mutableStateOf(false) }
    var volumeValue by remember { mutableStateOf(100) }
    val icon by remember { derivedStateOf { if (volumeValue > 1) VolumeOnIcon else VolumeOffIcon } }
    val tint by remember { derivedStateOf { if (volumeValue > 1) Color.White else Color.Gray } }
//    val icon = if (isVolumeOn) VolumeOnIcon else VolumeOffIcon
//    val tint = if (isVolumeOn) Color.White else Color.Gray

    Menu(
//        modifier = modifier,
        state = volumeSelectionState
    ) {
        MenuButton {
//            BasicText("Toggle the menu")
            VolumeContent(
                modifier = modifier,
                icon = icon,
                volumeValue = volumeValue,
                tint = tint,
                onVolumeClick = { volumeSelectionState.expanded = true }
            )
        }

        MenuContent {
            MenuItem(onClick = { volumeSelectionState.expanded = false }) {
//                BasicText("Close this menu")
                StretchySlider(
                    defaultValue = volumeValue.toFloat(),
                    onValueChange = { volumeValue = it }
                )
            }
        }
    }

//    VolumeContent(
//        modifier = modifier,
//        icon = icon,
//        tint = tint,
//        onVolumeClick = { volumeSelectionState.value = true }
//    )
//
//    VolumeSelection(
//        modifier = modifier.clickable { volumeSelectionState.value = true },
////        icon = icon,
//        state = volumeSelectionState,
//        openMenu = volumeSelectionState.value,
//        onOpenMenu = { volumeSelectionState.value = it },
//
////        statusText = statusText,
////        tint = tint,
//    )

}

@Composable
private fun VolumeContent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    volumeValue: Int,
    tint: Color,
    onVolumeClick: () -> Unit,
) {

    Box(
        modifier = modifier.wrapContentSize()
    ) {

        Column(
            modifier = Modifier.clickable { onVolumeClick() },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VolumeSelection(
    modifier: Modifier = Modifier,
    state: MenuState,
    openMenu: Boolean,
    onOpenMenu: (Boolean) -> Unit,
) {

//    if (!openMenu) return

    Menu(state = state) {
        MenuButton {
            BasicText("Toggle the menu")
        }

        MenuContent {
            MenuItem(onClick = { state.expanded = false }) {
                BasicText("Close this menu")
            }
        }
    }
}