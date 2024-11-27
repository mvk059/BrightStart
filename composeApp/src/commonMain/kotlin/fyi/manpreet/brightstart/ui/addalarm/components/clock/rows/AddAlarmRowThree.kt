package fyi.manpreet.brightstart.ui.addalarm.components.clock.rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.ui.components.button.CheckIcon
import fyi.manpreet.brightstart.ui.components.button.CloseIcon
import fyi.manpreet.brightstart.ui.components.button.RoundButton

@Composable
fun BoxScope.AddAlarmRowThree(
    modifier: Modifier = Modifier,
    timeLeftForAlarm: String,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    Row(
        modifier = modifier.fillMaxSize().align(Alignment.BottomCenter),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {

        Column(
            modifier = Modifier.weight(0.2f)
        ) {
            RoundButton(
                modifier = Modifier.align(Alignment.Start),
                icon = CloseIcon,
                onClick = onCloseClick
            )
        }

        Column(
            modifier = Modifier.weight(0.6f)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.CenterHorizontally),
                text = timeLeftForAlarm,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }

        Column(
            modifier = Modifier.weight(0.2f)
        ) {
            RoundButton(
                modifier = Modifier.align(Alignment.End),
                icon = CheckIcon,
                onClick = onAddClick
            )
        }
    }
}
