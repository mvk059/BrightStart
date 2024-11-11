package fyi.manpreet.brightstart.ui.components.clock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    onHourIndexUpdate: (Int) -> Unit,
    onMinuteIndexUpdate: (Int) -> Unit,
    onTimePeriodIndexUpdate: (Int) -> Unit,
    onTimeScrollingUpdate: () -> Unit,
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
        config = alarmTimeSelector.config,
        onScrollingStopped = onTimeScrollingUpdate,
    )
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
    config: TimeConfig,
    onScrollingStopped: () -> Unit,
) {

    val is24Hour = timeFormat == TimeFormat.TWENTY_FOUR_HOUR

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors().copy(
            contentColor = Color.White,
        )
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SwipeLazyColumn(
                modifier = Modifier.weight(if (is24Hour) 0.5f else 0.4f),
                selectedIndex = selectedHourIndex,
                onSelectedIndexChange = onSelectedHourIndexChange,
                items = hours.map { if (it.value.toString().length == 1) "0${it.value}" else it.value.toString() },
                alignment = Alignment.CenterEnd,
                configuration = config,
                height = config.height,
                onScrollingStopped = onScrollingStopped
            )

            SwipeLazyColumn(
                modifier = Modifier.weight(if (is24Hour) 0.5f else 0.2f),
                selectedIndex = selectedMinuteIndex,
                onSelectedIndexChange = onSelectedMinuteIndexChange,
                items = minutes.map { if (it.value.toString().length == 1) "0${it.value}" else it.value.toString() },
                textAlign = if (is24Hour) TextAlign.Start else TextAlign.Center,
                alignment = if (is24Hour) Alignment.CenterStart else Alignment.Center,
                configuration = config,
                height = config.height,
                onScrollingStopped = onScrollingStopped
            )

            if (!is24Hour) {
                SwipeLazyColumn(
                    modifier = Modifier.weight(0.4f),
                    selectedIndex = selectedTimeOfDayIndex,
                    onSelectedIndexChange = onSelectedTimeOfDayIndexChange,
                    items = timePeriod.map { it.value.toString() },
                    alignment = Alignment.CenterStart,
                    configuration = config,
                    height = config.height,
                    isScrollingToSelectedItemEnabled = true,
                    onScrollingStopped = onScrollingStopped
                )
            }
        }
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
                        .align(alignment)
                        .scale(scale),
                    style = if (isSelected) configuration.selectedTimeTextStyle else configuration.timeTextStyle,
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