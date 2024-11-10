package fyi.manpreet.brightstart.ui.addalarm.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.components.name.Name
import fyi.manpreet.brightstart.ui.components.repeat.Repeat
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoxScope.AddAlarmRowTwo(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    repeatDays: String,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
) {

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Name(
            modifier = Modifier.weight(0.5f).background(Color.Red),
            onNameUpdate = onNameUpdate,
        )
        Repeat(
            modifier = Modifier.weight(0.5f).background(Color.LightGray),
            alarmDays = alarm.alarmDays,
            repeatDays = repeatDays,
            onRepeatUpdate = onRepeatUpdate,
        )
    }
}

@Composable
@Preview
fun AddAlarmRowTwoPreview() {
    MaterialTheme {
        Box {
//            AddAlarmRowTwo()
        }
    }
}
