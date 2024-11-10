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
import fyi.manpreet.brightstart.ui.components.sound.Sound
import fyi.manpreet.brightstart.ui.components.vibrate.Vibrate
import fyi.manpreet.brightstart.ui.components.volume.Volume
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoxScope.AddAlarmRowOne(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    openRingtonePicker: () -> Unit,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
) {

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Sound(
            modifier = Modifier.weight(0.33f).background(Color.Cyan),
            alarmName = alarm.ringtoneName,
            openRingtonePicker = openRingtonePicker,
        )
        Volume(
            modifier = Modifier.weight(0.33f).background(Color.Magenta),
            onVolumeUpdate = onVolumeUpdate,
        )
        Vibrate(
            modifier = Modifier.weight(0.33f).background(Color.Yellow),
            onVibrateUpdate = onVibrateUpdate,
        )
    }
}

@Composable
@Preview
fun AddAlarmRowOnePreview() {
    MaterialTheme {
        Box {
//            AddAlarmRowOne()
        }
    }
}
