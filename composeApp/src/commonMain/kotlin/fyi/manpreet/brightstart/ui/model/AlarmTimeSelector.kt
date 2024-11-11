package fyi.manpreet.brightstart.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.jvm.JvmInline

@JvmInline
value class Hour(val value: Int)

@JvmInline
value class Minute(val value: Int)

@JvmInline
value class TimePeriod(val value: TimePeriodValue)

enum class TimeFormat {
    TWELVE_HOUR,
    TWENTY_FOUR_HOUR
}

enum class TimePeriodValue {
    AM,
    PM
}

data class AlarmTimeSelector(
    val timeFormat: TimeFormat = defaultTimeFormat(),
    val hours: List<Hour> = getHours(),
    val minutes: List<Minute> = getMinutes(),
    val timePeriod: List<TimePeriod> = getTimePeriods(),
    val selectedHourIndex: Int = getSelectedHourIndex(),
    val selectedMinuteIndex: Int = getSelectedMinuteIndex(),
    val selectedTimePeriodIndex: Int = getSelectedTimePeriodIndex(),
    val selectedTime: AlarmSelectedTime = AlarmSelectedTime(),
    val config: TimeConfig = TimeConfig(),
) {

    data class AlarmSelectedTime(
        val hour: Hour = getSelectedHour(),
        val minute: Minute = getSelectedMinute(),
        val timePeriod: TimePeriod = getSelectedTimePeriod(),
    )

    data class TimeConfig(
        val height: Dp = 200.dp,
        val numberOfTimeRowsDisplayed: Int = 3,
        val selectedTimeScaleFactor: Float = 1.2f,
        val timeTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black.copy(alpha = 0.5f) // Color(0xFFcdccd9)
        ),
        val selectedTimeTextStyle: TextStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            color = Color.Black
        )
    )

    companion object {
        const val LIST_ITEM_COUNT = 10

        private fun defaultTimeFormat() = TimeFormat.TWELVE_HOUR

        fun getHours(): List<Hour> {
            val hours = mutableListOf<Hour>()
            repeat(LIST_ITEM_COUNT) {
                when (defaultTimeFormat()) {
                    TimeFormat.TWELVE_HOUR -> (1..12).forEach { hours.add(Hour(it)) }
                    TimeFormat.TWENTY_FOUR_HOUR -> (0..23).forEach { hours.add(Hour(it)) }
                }
            }
            return hours
        }

        fun getMinutes(): List<Minute> {
            val minutes = mutableListOf<Minute>()
            repeat(LIST_ITEM_COUNT) {
                (0..59).forEach { minutes.add(Minute(it)) }
            }
            return minutes
        }

        fun getTimePeriods(): List<TimePeriod> = buildList {
            add(TimePeriod(TimePeriodValue.AM))
            add(TimePeriod(TimePeriodValue.PM))
        }

        fun getSelectedHour() = getHours()[getSelectedHourIndex()]

        fun getSelectedMinute() = getMinutes()[getSelectedMinuteIndex()]

        fun getSelectedTimePeriod() = getTimePeriods()[getSelectedTimePeriodIndex()]

        fun getSelectedHourIndex() = getHours().size / 2

        fun getSelectedMinuteIndex() = getMinutes().size / 2

        fun getSelectedTimePeriodIndex() = getTimePeriods().size / 2
    }

}
