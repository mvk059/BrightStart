package fyi.manpreet.brightstart.ui.addalarm.components.name

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_name
import brightstart.composeapp.generated.resources.add_alarm_name_default
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun Name(
    modifier: Modifier = Modifier,
    onNameUpdate: (AddAlarmEvent) -> Unit,
) {

    // TODO check for animations
    var expanded by remember { mutableStateOf(false) }
    var alarmName by remember { mutableStateOf(TextFieldValue()) }
    val defaultAlarmName = stringResource(Res.string.add_alarm_name_default)

    NameContent(
        modifier = modifier.clickable { expanded = !expanded },
        alarmName = alarmName.text.ifEmpty { defaultAlarmName },
    )

    NameTextField(
        isExpanded = expanded,
        text = alarmName,
        placeholder = alarmName.text.ifEmpty { defaultAlarmName },
        onTextChange = {
            alarmName = it
            onNameUpdate(AddAlarmEvent.NameUpdate(it.text))
        },
        onDone = { expanded = false },
    )
}

@Composable
private fun NameContent(
    modifier: Modifier = Modifier,
    alarmName: String,
) {
    Box(modifier = modifier) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                modifier = Modifier.padding(top = 4.dp),
                imageVector = NameIcon,
                contentDescription = null,
                tint = Color.White,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(Res.string.add_alarm_name),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = alarmName,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp
                ),
                color = Color.LightGray,
            )
        }
    }
}

@Composable
fun NameTextField(
    isExpanded: Boolean,
    text: TextFieldValue,
    placeholder: String,
    onTextChange: (TextFieldValue) -> Unit,
    onDone: () -> Unit,
) {

    if (!isExpanded) return

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        if (isExpanded) {
            focusRequester.requestFocus()
            delay(100)
            keyboardController?.show()
        }
    }

    TextField(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = text,
        onValueChange = { onTextChange(it) },
        placeholder = { Text(placeholder, color = Color.Gray) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onDone()
            }
        )
    )
}
