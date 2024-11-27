package fyi.manpreet.brightstart.ui.addalarm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.addalarm.components.name.Name
import fyi.manpreet.brightstart.ui.addalarm.components.sound.Sound
import fyi.manpreet.brightstart.ui.addalarm.components.vibrate.Vibrate

@Composable
fun BoxScope.AddAlarmRowTwo(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    openRingtonePicker: () -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Sound(
            modifier = Modifier.weight(0.33f),
            alarmName = alarm.ringtoneName.value,
            openRingtonePicker = openRingtonePicker,
        )
        Name(
            modifier = Modifier.weight(0.33f),
            onNameUpdate = onNameUpdate,
        )
        Vibrate(
            modifier = Modifier.weight(0.33f),
            onVibrateUpdate = onVibrateUpdate,
        )
    }
}