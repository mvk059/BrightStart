package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.home.HomeEvent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmTimeItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    onAlarmStatusChange: (HomeEvent) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 0.dp),
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
        ) {

            Text(
                modifier = Modifier.align(Alignment.Bottom),
                text = alarm.time.value,
                style = MaterialTheme.typography.displayLarge.copy(
                    letterSpacing = 2.sp
                ),
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(start = 8.dp, bottom = 12.dp),
                text = alarm.timePeriod.value.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                modifier = Modifier.padding(start = 0.dp),
                checked = alarm.isActive.value,
                onCheckedChange = { onAlarmStatusChange(HomeEvent.ToggleAlarm(alarm, it)) },
            )
        }

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = alarm.name.value, // TODO add to model
            style = MaterialTheme.typography.bodyLarge
        )
    }

}

@Composable
@Preview
fun AlarmTimeItemPreview() {
    MaterialTheme {
//        AlarmTimeItem()
    }
}
