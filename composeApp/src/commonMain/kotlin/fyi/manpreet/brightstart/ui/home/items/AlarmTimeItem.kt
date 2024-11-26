package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
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
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp),
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
        ) {

            Text(
//                modifier = Modifier.align(Alignment.Bottom),
                text = alarm.time.value,
                style = MaterialTheme.typography.displayMedium.copy(
                    letterSpacing = 2.sp
                ),
            )

            Column(
                modifier = Modifier.fillMaxHeight().padding(start = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                // TODO Add tint based on theme
                Icon(
                    imageVector = alarm.icon,
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.padding(start = 0.dp, bottom = 0.dp),
                    text = alarm.timePeriod.value.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                modifier = Modifier.padding(start = 0.dp),
                checked = alarm.isActive.value,
                onCheckedChange = { onAlarmStatusChange(HomeEvent.ToggleAlarm(alarm, it)) },
            )
        }

        if (alarm.name.value.isEmpty()) return

        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 8.dp),
            text = alarm.name.value, // TODO add to model
            style = MaterialTheme.typography.bodySmall
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
