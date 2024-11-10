package fyi.manpreet.brightstart.ui.addalarm.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fyi.manpreet.brightstart.ui.components.clock.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoxScope.AddAlarmClockOne(
    modifier: Modifier = Modifier
) {

    Clock(modifier = modifier.fillMaxSize().background(color = Color.Red))

}

@Composable
@Preview
fun AddAlarmClockOnePreview() {
    MaterialTheme {
//        AddAlarmClockOne()
    }
}
