package fyi.manpreet.brightstart.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.home.items.SwipeToDeleteContainer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmList(
    modifier: Modifier = Modifier,
    alarms: List<Alarm>,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmStatusChange: (HomeEvent) -> Unit,
) {

    LazyColumn(
        modifier = modifier.wrapContentSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = alarms,
            key = { alarm -> alarm.id },
        ) { alarm ->

            SwipeToDeleteContainer(
                item = alarm,
                onDelete = { onAlarmStatusChange(HomeEvent.DeleteAlarm(it)) },
            ) {
                AlarmCard(
                    alarm = alarm,
                    onAlarmClick = onAlarmClick,
                    onAlarmStatusChange = onAlarmStatusChange,
                )
            }
        }
    }
}

@Composable
@Preview
fun AlarmListPreview() {
    MaterialTheme {
        AlarmList(
            alarms = emptyList(),
            onAlarmClick = {},
            onAlarmStatusChange = {},
        )
    }
}
