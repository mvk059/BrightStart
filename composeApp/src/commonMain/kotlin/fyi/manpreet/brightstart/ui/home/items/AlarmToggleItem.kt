package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.home.HomeEvent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmToggleRowItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    onAlarmStatusChange: (HomeEvent) -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .padding(top = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = alarm.name.value,
        )

        Switch(
            checked = alarm.isActive.value,
            onCheckedChange = { onAlarmStatusChange(HomeEvent.ToggleAlarm(alarm, it)) },
            modifier = Modifier.padding(start = 0.dp),
        )
    }
}

@Composable
@Preview
fun AlarmToggleRowItemPreview() {
    MaterialTheme {
//        AlarmToggleRowItem() TODO
    }
}
