package fyi.manpreet.brightstart.ui.components.repeat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_repeat
import com.composables.core.Menu
import com.composables.core.MenuButton
import com.composables.core.MenuContent
import com.composables.core.MenuItem
import com.composables.core.rememberMenuState
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.components.name.NameIcon
import fyi.manpreet.brightstart.ui.model.AlarmDaysItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun Repeat(
    modifier: Modifier = Modifier,
    alarmDaysItem: List<AlarmDaysItem>,
    repeatDays: String,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
) {

    // TODO Fix chip size
    // TODO Check in landscape
    // TODO Fix color scheme of chips
    // TODO Logic to handle repeat days text
    val repeatMenuState = rememberMenuState(expanded = false)

    Menu(
        state = repeatMenuState
    ) {

        MenuButton {
            RepeatContent(
                modifier = modifier,
                repeatDays = repeatDays,
                onNameClick = { repeatMenuState.expanded = true }
            )
        }

        MenuContent(
            alignment = Alignment.CenterHorizontally,
        ) {
            MenuItem(onClick = { repeatMenuState.expanded = false }) {
                RepeatRow(
                    isExpanded = repeatMenuState.expanded,
                    alarmDaysItem = alarmDaysItem,
                    onRepeatUpdate = onRepeatUpdate
                )
            }
        }
    }
}

@Composable
private fun RepeatContent(
    modifier: Modifier = Modifier,
    repeatDays: String,
    onNameClick: () -> Unit,
) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {

        Column(
            modifier = Modifier.clickable { onNameClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                imageVector = NameIcon,
                contentDescription = null,
                tint = Color.White,
            )

            Text(
                text = stringResource(Res.string.add_alarm_repeat),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )

            Text(
                text = repeatDays,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray,
            )
        }
    }
}

@Composable
fun RepeatRow(
    isExpanded: Boolean,
    alarmDaysItem: List<AlarmDaysItem>,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
) {

    if (!isExpanded) return


    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF36363D), shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        alarmDaysItem.forEach { item ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)

                    .background(
                        color = if (item.isSelected) Color.LightGray else Color(0xFF1E1E26),
                        shape = RoundedCornerShape(8.dp),
                    )
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onRepeatUpdate(AddAlarmEvent.RepeatUpdate(item))
                    },
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 16.dp),
                    text = item.day,
                    color = Color.White
                )
            }
        }
    }
}
