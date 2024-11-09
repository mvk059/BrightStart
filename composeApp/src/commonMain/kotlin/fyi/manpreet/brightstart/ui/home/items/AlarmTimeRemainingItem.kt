package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.data.model.Alarm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmTimeRemainingItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {

        Text("Time remaining")
    }

}

@Composable
@Preview
fun AlarmTimeRemainingItemPreview() {
    MaterialTheme {
//        AlarmTimeRemainingItem()
    }
}
