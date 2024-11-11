package fyi.manpreet.brightstart.ui.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.addalarm.components.name.Name
import fyi.manpreet.brightstart.ui.addalarm.components.repeat.AllAlarmRepeat
import fyi.manpreet.brightstart.ui.addalarm.components.sound.Sound
import fyi.manpreet.brightstart.ui.addalarm.components.vibrate.Vibrate
import fyi.manpreet.brightstart.ui.addalarm.components.volume.Volume
import fyi.manpreet.brightstart.ui.addalarm.components.toprow.AddAlarmTopRow
import fyi.manpreet.brightstart.ui.components.clock.TimePicker
import fyi.manpreet.brightstart.ui.model.AlarmTimeSelector
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 *
 * Time Picker: Takes 70% of the screen
 * TODO Sound, Volume, Vibrate, Repeat, Name, Time left for alarm
 * TODO Close and Save button at bottom left and right respectively
 * TODO Handle status bar and navigation bar background colors
 * TODO Set fonts
 * TODO Setup theme
 * TODO Set text size/style based on theme for all text
 * TODO Keep weight of both the rows same in terms of height as well
 */
@Composable
fun AddAlarm(
    modifier: Modifier = Modifier,
    alarm: StateFlow<Alarm>,
    alarmTimeSelector: StateFlow<AlarmTimeSelector>,
    onHourIndexUpdate: (Int) -> Unit,
    onMinuteIndexUpdate: (Int) -> Unit,
    onTimePeriodIndexUpdate: (Int) -> Unit,
    onTimeScrollingUpdate: () -> Unit,
    repeatDays: StateFlow<String>,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
    openRingtonePicker: () -> Unit,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    // TODO Add scaffold for app bar?
    // TODO Adjust weight accordingly
    // TODO Increase padding of each component slightly for better clickability
    println("AddAlarmScreen: ${alarm.value}")
    val alarm = alarm.collectAsStateWithLifecycle()
    val repeatDays = repeatDays.collectAsStateWithLifecycle()
    val alarmTimeSelector = alarmTimeSelector.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFf5f5f5))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        AddAlarmTopRow(
            onSaveClick = onAddClick,
            onCloseClick = onCloseClick,
        )

        TimePicker(
            alarmTimeSelector = alarmTimeSelector.value,
            onHourIndexUpdate = onHourIndexUpdate,
            onMinuteIndexUpdate = onMinuteIndexUpdate,
            onTimePeriodIndexUpdate = onTimePeriodIndexUpdate,
            onTimeScrollingUpdate = onTimeScrollingUpdate,
        )

        AllAlarmRepeat(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            alarmDays = alarm.value.alarmDays,
            repeatTitle = repeatDays.value,
            onRepeatUpdate = onRepeatUpdate,
        )

        Name(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            alarmName = alarm.value.name,
            onNameUpdate = onNameUpdate,
        )

        Sound(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            alarmName = alarm.value.ringtoneName,
            openRingtonePicker = openRingtonePicker,
        )

        Volume(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            volumeValue = alarm.value.volume,
            onVolumeUpdate = onVolumeUpdate,
        )

        Vibrate(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            vibrationStatus = alarm.value.vibrationStatus,
            onVibrateUpdate = onVibrateUpdate,
        )
    }
}

@Preview
@Composable
fun AddAlarmPreview() {
    MaterialTheme {
//        AddAlarm()
    }

}