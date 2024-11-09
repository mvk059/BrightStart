package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fyi.manpreet.brightstart.data.model.Alarm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmRepeatItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFF36363D))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        alarm.alarmDays.forEach { item ->

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
                    text = "*\n${item.day}",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        lineHeight = 12.sp,
                    )
                )
            }
        }
    }

}

@Composable
@Preview
fun AlarmRepeatItemPreview() {
    MaterialTheme {
//        AlarmRepeatItem()
    }
}
