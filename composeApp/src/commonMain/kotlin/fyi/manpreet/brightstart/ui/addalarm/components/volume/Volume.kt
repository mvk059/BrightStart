package fyi.manpreet.brightstart.ui.addalarm.components.volume

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_volume
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Volume(
    modifier: Modifier = Modifier,
    volumeValue: Int,
    onVolumeUpdate: (AddAlarmEvent) -> Unit,
) {

    var volumeValue by remember { mutableStateOf(volumeValue) }

    Column(
        modifier = modifier,
    ) {
        VolumeTitleRow(
            volumeValue = volumeValue,
        )

        // TODO Get from config
        // Customise this
        Slider(
            value = volumeValue.toFloat(),
            onValueChange = {
                volumeValue = it.toInt()
                onVolumeUpdate(AddAlarmEvent.VolumeUpdate(it.toInt()))
            },
            valueRange = 0f..100f,
        )
    }
}

@Composable
private fun VolumeTitleRow(
    modifier: Modifier = Modifier,
    volumeValue: Int,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(Res.string.add_alarm_volume),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )

        // Have different font styles slightly
        Text(
            text = volumeValue.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
        )
    }
}
