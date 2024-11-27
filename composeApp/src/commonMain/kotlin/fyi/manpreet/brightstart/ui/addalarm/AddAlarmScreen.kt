package fyi.manpreet.brightstart.ui.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.addalarm.components.clock.Clock
import fyi.manpreet.brightstart.ui.addalarm.components.clock.ClockRev
import fyi.manpreet.brightstart.ui.addalarm.components.AddAlarmRowThree
import fyi.manpreet.brightstart.ui.addalarm.components.AddAlarmRowTwo
import fyi.manpreet.brightstart.ui.addalarm.components.repeat.AllAlarmRepeat
import fyi.manpreet.brightstart.ui.model.TimePeriodValue
import kotlinx.coroutines.flow.StateFlow

/**
 *
 * TODO Use device locale to determine 12/24 hour format
 * TODO Handle status bar and navigation bar background colors
 */
@Composable
fun AddAlarm(
    modifier: Modifier = Modifier,
    alarm: StateFlow<Alarm>,
    onHourIndexUpdate: (Int) -> Unit,
    onMinuteIndexUpdate: (Int) -> Unit,
    onTimePeriodIndexUpdate: (TimePeriodValue) -> Unit,
    onVibrateUpdate: (AddAlarmEvent) -> Unit,
    onNameUpdate: (AddAlarmEvent) -> Unit,
    onRepeatUpdate: (AddAlarmEvent) -> Unit,
    openRingtonePicker: () -> Unit,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    val alarmValue = alarm.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1E1E26))
    ) {

        Row(
            modifier = Modifier
                .weight(0.60f)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Clock(
                    modifier = Modifier.fillMaxSize(),
                    hour = alarmValue.time.hour.value,
                    onHourChange = onHourIndexUpdate,
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                ClockRev(
                    modifier = Modifier.fillMaxSize(),
                    minute = alarmValue.time.minute.value,
                    onMinuteChange = onMinuteIndexUpdate,
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.40f)
                .fillMaxWidth()
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                AllAlarmRepeat(
                    modifier = Modifier
                        .fillMaxWidth(),
                    alarmDays = alarm.value.alarmDays,
                    repeatTitle = alarm.value.repeatDays,
                    onRepeatUpdate = onRepeatUpdate,
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                AddAlarmRowTwo(
                    modifier = Modifier.padding(top = 24.dp),
                    alarm = alarmValue,
                    openRingtonePicker = openRingtonePicker,
                    onNameUpdate = onNameUpdate,
                    onVibrateUpdate = onVibrateUpdate,
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                AddAlarmRowThree(
                    timeLeftForAlarm = alarmValue.timeLeftForAlarm,
                    onAddClick = onAddClick,
                    onCloseClick = onCloseClick
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TimePeriodValue.entries.forEachIndexed { index, periodValue ->
            // TODO Animate size change on selection.
            Text(
                modifier = Modifier
                    .clickable { onTimePeriodIndexUpdate(if (index == 0) TimePeriodValue.AM else TimePeriodValue.PM) }
                    .padding(horizontal = 16.dp),
                text = periodValue.name,
                color = if (alarmValue.timePeriod.value == periodValue) Color.LightGray else Color.DarkGray,
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = alarmValue.time.value,
            modifier = Modifier.align(Alignment.Center).padding(top = 24.dp),
            style = MaterialTheme.typography.displaySmall,
            color = Color.White
        )

    }
}
