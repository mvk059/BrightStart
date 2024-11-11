package fyi.manpreet.brightstart.ui.addalarm.components.name

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.add_alarm_name
import brightstart.composeapp.generated.resources.add_alarm_name_default
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun Name(
    modifier: Modifier = Modifier,
    alarmName: String,
    onNameUpdate: (AddAlarmEvent) -> Unit,
) {

    // TODO Customise text field
    // TODO add cursor at the end of the text field if there is text and remove focus of cursor
    // TODO Fix cursor not moving
    var alarmName by remember { mutableStateOf(TextFieldValue(alarmName)) }
    val defaultAlarmName = stringResource(Res.string.add_alarm_name_default)

    Column(
        modifier = modifier,
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.add_alarm_name),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )

        NameTextField(
            alarmName = TextFieldValue(alarmName.text.ifEmpty { defaultAlarmName }),
            onTextChange = {
                alarmName = it
                onNameUpdate(AddAlarmEvent.NameUpdate(it.text))
            },
        )
    }

}

@Composable
private fun NameTextField(
    alarmName: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = alarmName,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusRequester.requestFocus()
//                onDone()
            }
        )
    )
}
