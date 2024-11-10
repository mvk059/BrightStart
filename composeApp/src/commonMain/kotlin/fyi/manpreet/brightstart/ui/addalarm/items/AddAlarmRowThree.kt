package fyi.manpreet.brightstart.ui.addalarm.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoxScope.AddAlarmRowThree(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        RoundButton(
            modifier = Modifier.weight(0.2f).background(Color.Cyan),
            icon = CloseIcon,
            onClick = onCloseClick
        )

        Text(
            modifier = Modifier
                .weight(0.6f)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(Color.Magenta),
            text = "Time left for alarm here",
            textAlign = TextAlign.Center,
        ) // TODO

        RoundButton(
            modifier = Modifier.weight(0.2f).background(Color.Yellow),
            icon = CheckIcon,
            onClick = onAddClick
        )
    }
}

@Composable
@Preview
fun AddAlarmRowThreePreview() {
    MaterialTheme {
        Box {
//            AddAlarmRowThree()
        }
    }
}
