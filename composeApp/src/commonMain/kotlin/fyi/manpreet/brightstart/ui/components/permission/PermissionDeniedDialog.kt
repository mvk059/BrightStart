package fyi.manpreet.brightstart.ui.components.permission

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import brightstart.composeapp.generated.resources.Res
import brightstart.composeapp.generated.resources.common_cancel
import brightstart.composeapp.generated.resources.permission_denied_message
import brightstart.composeapp.generated.resources.permission_denied_settings
import brightstart.composeapp.generated.resources.permission_denied_title
import fyi.manpreet.brightstart.platform.permission.Permission
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PermissionDeniedDialog(
    modifier: Modifier = Modifier,
    onSettingsClick: (AddAlarmEvent) -> Unit,
    onDismissRequest: (AddAlarmEvent) -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismissRequest(AddAlarmEvent.DismissPermissionDialog) },
        title = {
            Text(text = stringResource(Res.string.permission_denied_title))
        },
        text = {
            Text(text = stringResource(Res.string.permission_denied_message))
        },
        confirmButton = {
            TextButton(
                onClick = { onSettingsClick(AddAlarmEvent.OpenSettings(Permission.NOTIFICATION)) }
            ) {
                Text(text = stringResource(Res.string.permission_denied_settings))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest(AddAlarmEvent.DismissPermissionDialog) }) {
                Text(text = stringResource(Res.string.common_cancel))
            }
        }
    )
}

@Composable
@Preview
fun PermissionDeniedDialogPreview() {
//    MaterialTheme {
//        PermissionDeniedDialog()
//    }
}
