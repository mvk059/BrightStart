package fyi.manpreet.brightstart.ui.components.name

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_name
import brightstart.composeapp.generated.resources.add_alarm_name_default
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.home.HomeEvent
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
        modifier = modifier,
        alarmName = alarmName.text.ifEmpty { defaultAlarmName },
        onNameClick = { expanded = !expanded }
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
    onNameClick: () -> Unit,
) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {

        Column(
            modifier = Modifier.clickable { onNameClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO Set tint to grey on disable and white/remove to enable
            Icon(
                imageVector = NameIcon,
                contentDescription = null,
                tint = Color.White,
            )

            Text(
                text = stringResource(Res.string.add_alarm_name),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )

            Text(
                text = alarmName,
                style = MaterialTheme.typography.bodySmall,
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
