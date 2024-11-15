package fyi.manpreet.brightstart.ui.components.clock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fyi.manpreet.brightstart.ui.components.clock.component.AddAlarmTimePickerShape
import fyi.manpreet.brightstart.ui.components.clock.component.SwipeLazyColumn
import fyi.manpreet.brightstart.ui.model.AlarmTimeSelector
import fyi.manpreet.brightstart.ui.model.AlarmTimeSelector.TimeConfig
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import fyi.manpreet.brightstart.ui.model.TimeFormat
import fyi.manpreet.brightstart.ui.model.TimePeriod
import fyi.manpreet.brightstart.ui.model.noRippleClickable
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.collections.map

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    alarmTimeSelector: AlarmTimeSelector,
    timeLeftForAlarm: String,
    onHourIndexUpdate: (Int) -> Unit,
    onMinuteIndexUpdate: (Int) -> Unit,
    onTimePeriodIndexUpdate: (Int) -> Unit,
    onTimeScrollingUpdate: () -> Unit,
) {

    val bumpWidth35x = 0.35f
    val bumpHeight10x = 0.30f
    val bumpWidth = remember { mutableFloatStateOf(0f) }
    val bumpHeight = remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp),
        shape = AddAlarmTimePickerShape(),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White,
        )
    ) {
        Box(
            modifier = Modifier.onGloballyPositioned {
                val width = it.size.width
                val height = it.size.height
                bumpWidth.value = width * 0.07f
                bumpHeight.value = height * 0.12f
                println("TimePicker: width: $width, height: $height")
            }
        ) {

            TimePickerView(
                modifier = modifier,
                hours = alarmTimeSelector.hours,
                selectedHourIndex = alarmTimeSelector.selectedHourIndex,
                onSelectedHourIndexChange = onHourIndexUpdate,
                minutes = alarmTimeSelector.minutes,
                selectedMinuteIndex = alarmTimeSelector.selectedMinuteIndex,
                onSelectedMinuteIndexChange = onMinuteIndexUpdate,
                timePeriod = alarmTimeSelector.timePeriod,
                selectedTimeOfDayIndex = alarmTimeSelector.selectedTimePeriodIndex,
                onSelectedTimeOfDayIndexChange = onTimePeriodIndexUpdate,
                timeFormat = alarmTimeSelector.timeFormat,
                timeLeftForAlarm = timeLeftForAlarm,
                config = alarmTimeSelector.config,
                onScrollingStopped = onTimeScrollingUpdate,
            )

            Text(
                modifier = Modifier
                    .offset(
                        x = 0.dp, //with(density) { bumpWidth.value.toDp() },
                        y = with(density) { -bumpHeight.value.toDp() }
                    )
                    .padding(8.dp),
                text = " * Alarm Set",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray.copy(alpha = 0.7f),
            )
        }
    }
}

// https://github.com/vsnappy1/ComposeDatePicker/tree/main
@Composable
private fun TimePickerView(
    modifier: Modifier = Modifier,
    hours: List<Hour>,
    selectedHourIndex: Int,
    onSelectedHourIndexChange: (Int) -> Unit,
    minutes: List<Minute>,
    selectedMinuteIndex: Int,
    onSelectedMinuteIndexChange: (Int) -> Unit,
    timePeriod: List<TimePeriod>,
    selectedTimeOfDayIndex: Int,
    onSelectedTimeOfDayIndexChange: (Int) -> Unit,
    timeFormat: TimeFormat,
    timeLeftForAlarm: String,
    config: TimeConfig,
    onScrollingStopped: () -> Unit,
) {

    val is24Hour = timeFormat == TimeFormat.TWENTY_FOUR_HOUR

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier.padding(top = 8.dp), //.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // TODO Animate size change on selection. Handle selection
            Text(
//                modifier = Modifier.weight(1f),
                text = "AM",
                color = Color.DarkGray,
            )

            Spacer(Modifier.width(24.dp))

            Text(
//                modifier = Modifier.weight(1f),
                text = "PM",
                color = Color.LightGray,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SwipeLazyColumn(
//                modifier = Modifier.weight(if (is24Hour) 0.5f else 0.4f),
                modifier = Modifier.weight(1f),
                selectedIndex = selectedHourIndex,
                onSelectedIndexChange = onSelectedHourIndexChange,
                items = hours.map { if (it.value.toString().length == 1) "0${it.value}" else it.value.toString() },
                alignment = Alignment.CenterEnd,
                configuration = config,
                height = config.height,
                onScrollingStopped = onScrollingStopped
            )

            Text(":")

            SwipeLazyColumn(
//                modifier = Modifier.weight(if (is24Hour) 0.5f else 0.2f),
                modifier = Modifier.weight(1f),
                selectedIndex = selectedMinuteIndex,
                onSelectedIndexChange = onSelectedMinuteIndexChange,
                items = minutes.map { if (it.value.toString().length == 1) "0${it.value}" else it.value.toString() },
                textAlign = if (is24Hour) TextAlign.Start else TextAlign.Center,
                alignment = if (is24Hour) Alignment.CenterStart else Alignment.CenterStart,
                configuration = config,
                height = config.height,
                onScrollingStopped = onScrollingStopped
            )

//            if (!is24Hour) {
//                SwipeLazyColumn(
////                    modifier = Modifier.weight(0.4f),
//                    modifier = Modifier.weight(1f),
//                    selectedIndex = selectedTimeOfDayIndex,
//                    onSelectedIndexChange = onSelectedTimeOfDayIndexChange,
//                    items = timePeriod.map { it.value.toString() },
//                    alignment = Alignment.CenterStart,
//                    configuration = config,
//                    height = config.height,
//                    isScrollingToSelectedItemEnabled = true,
//                    onScrollingStopped = onScrollingStopped
//                )
//            }

        }

        Text(
            text = timeLeftForAlarm,
            style = MaterialTheme.typography.bodySmall
        )
    }


}

@Composable
private fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>,
    textAlign: TextAlign = TextAlign.End,
    alignment: Alignment = Alignment.CenterStart,
    configuration: TimeConfig,
    isScrollingToSelectedItemEnabled: Boolean = false,
    height: Dp,
    onScrollingStopped: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isAutoScrolling by remember { mutableStateOf(false) }
    val listState = rememberLazyListState(selectedIndex)
    SwipeLazyColumn(
        modifier = modifier,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        isAutoScrolling = isAutoScrolling,
        height = height,
        isScrollingToSelectedItemEnabled = isScrollingToSelectedItemEnabled,
        numberOfRowsDisplayed = configuration.numberOfTimeRowsDisplayed,
        listState = listState,
        onScrollingStopped = {
            isAutoScrolling = false
            onScrollingStopped()
        }
    ) {
        // we add some empty rows at the beginning and end of list to make it feel that it is a center focused list
        val count = items.size + configuration.numberOfTimeRowsDisplayed - 1
        items(count) {
            SliderItem(
                value = it,
                selectedIndex = selectedIndex,
                items = items,
                configuration = configuration,
                alignment = alignment,
                textAlign = textAlign,
                height = height,
                onItemClick = { index ->
                    coroutineScope.launch {
                        isAutoScrolling = true
                        onSelectedIndexChange(index)
                        listState.animateScrollToItem(index)
                        isAutoScrolling = false
                        onScrollingStopped()
                    }
                }
            )
        }
    }
}

@Composable
private fun SliderItem(
    value: Int,
    selectedIndex: Int,
    items: List<String>,
    onItemClick: (Int) -> Unit,
    alignment: Alignment,
    configuration: TimeConfig,
    height: Dp,
    textAlign: TextAlign,
) {
    // this gap variable helps in maintaining list as center focused list
    val gap = configuration.numberOfTimeRowsDisplayed / 2
    val isSelected = value == selectedIndex + gap
    val scale by animateFloatAsState(targetValue = if (isSelected) configuration.selectedTimeScaleFactor else 1f)
    Box(
        modifier = Modifier
            .height(height / configuration.numberOfTimeRowsDisplayed)
            .padding(
                start = if (alignment == Alignment.CenterStart) 16.dp else 0.dp,
                end = if (alignment == Alignment.CenterEnd) 16.dp else 0.dp
            )
    ) {
        if (value >= gap && value < items.size + gap) {
            Box(modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    onItemClick(value - gap)
                }) {
                Text(
                    text = items[value - gap],
                    modifier = Modifier
                        .align(alignment),
//                        .scale(scale),
                    style =
                    if (isSelected) {
                        MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            fontSize = 48.sp
                        )
                    } else {
                        MaterialTheme.typography.bodyLarge.copy(
                            color = Color.LightGray,
                            fontSize = 38.sp
                        )
                    },
                    textAlign = textAlign
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTimePicker() {
//    TimePicker(onTimeSelected = { _: Int, _: Int -> })
}