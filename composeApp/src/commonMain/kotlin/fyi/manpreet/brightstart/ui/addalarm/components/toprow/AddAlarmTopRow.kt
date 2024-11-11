package fyi.manpreet.brightstart.ui.addalarm.components.toprow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddAlarmTopRow(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
) {

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // TODO Match icon to design
        Icon(
            modifier = Modifier.clickable { onCloseClick() },
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
        )

        // TODO Extract to strings.xml
        // TODO Make it look like design
        Button(
            onClick = onSaveClick
        ) {
            Text(text = "Save")
        }
    }

}