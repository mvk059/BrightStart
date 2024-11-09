package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.data.model.Alarm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmTimeItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
//            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .padding(top = 0.dp),
    ) {

        Row(
            modifier = Modifier.padding(vertical = 0.dp),
        ) {

            Text(
                text = alarm.time,
            )
        }

        Text(
            text = "Alarm in sometime", // TODO add to model
        )
    }

}

@Composable
@Preview
fun AlarmTimeItemPreview() {
    MaterialTheme {
//        AlarmTimeItem()
    }
}
