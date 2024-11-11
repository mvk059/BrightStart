package fyi.manpreet.brightstart.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.home.items.AlarmRepeatItem
import fyi.manpreet.brightstart.ui.home.items.AlarmTimeItem
import fyi.manpreet.brightstart.ui.home.items.AlarmTimeRemainingItem
import fyi.manpreet.brightstart.ui.home.items.AlarmToggleRowItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmStatusChange: (HomeEvent) -> Unit,
) {
    // TODO Add styles to text. Use Tokens
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onAlarmClick(alarm) }
            .padding(horizontal = 0.dp, vertical = 0.dp),
        shape = RoundedCornerShape(size = 16.dp),
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AlarmToggleRowItem(
                alarm = alarm,
                onAlarmStatusChange = onAlarmStatusChange,
            )

            // TODO Send only required items
            AlarmTimeItem(
                alarm = alarm,
            )

            AlarmRepeatItem(
                alarm = alarm,
            )

            AlarmTimeRemainingItem(
                alarm = alarm,
            )
        }
    }
}

@Composable
@Preview
fun AlarmCardPreview() {
    MaterialTheme {
//        AlarmCard()
    }
}
