package fyi.manpreet.brightstart.ui.home

import androidx.compose.foundation.layout.Spacer
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmList(
    modifier: Modifier = Modifier,
    alarms: List<Alarm>,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmStatusChange: (HomeEvent) -> Unit,
) {

    LazyColumn(
        modifier = modifier.wrapContentSize().padding(horizontal = 8.dp, vertical = 8.dp),  // Card Outside padding
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(alarms) { alarm ->
            AlarmCard(
                alarm = alarm,
                onAlarmClick = onAlarmClick,
                onAlarmStatusChange = onAlarmStatusChange,
            )

            Spacer(modifier = Modifier.padding(16.dp))
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
