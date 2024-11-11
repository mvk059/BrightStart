package fyi.manpreet.brightstart.ui.addalarm.components.repeat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_repeat
import fyi.manpreet.brightstart.data.model.Alarm.AlarmDays
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun AllAlarmRepeat(
    modifier: Modifier = Modifier,
    alarmDays: List<AlarmDays>,
    repeatTitle: String,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
) {

    // TODO Fix chip size
    // TODO Check in landscape
    // TODO Fix color scheme of chips
    // TODO Logic to handle repeat days text
    Column(
        modifier = modifier,
    ) {
        RepeatTitleRow(
            repeatTitle = repeatTitle,
        )

        Spacer(modifier = Modifier.height(8.dp))

        RepeatTileRow(
            alarmDays = alarmDays,
            onRepeatUpdate = onRepeatUpdate,
        )
    }
}

@Composable
private fun RepeatTitleRow(
    modifier: Modifier = Modifier,
    repeatTitle: String,
) {

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(Res.string.add_alarm_repeat),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )

        // Have different font styles slightly
        Text(
            text = repeatTitle.ifEmpty { "Weekdays" },
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
        )
    }
}

@Composable
private fun RepeatTileRow(
    alarmDays: List<AlarmDays>,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        alarmDays.forEach { item ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable {
                        onRepeatUpdate(AddAlarmEvent.RepeatUpdate(item))
                    }
                    .weight(1f)
                    .background(
                        color = if (item.isSelected) Color.Blue else Color.White,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically),
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 16.dp),
                    text = item.day,
                    color = if (item.isSelected) Color.White else Color.Black
                )
            }
        }
    }
}
