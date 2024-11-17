package fyi.manpreet.brightstart.platform.permission

import android.Manifest
import android.content.Context
import android.os.Build
import fyi.manpreet.brightstart.MainActivityUseCase
import fyi.manpreet.brightstart.platform.permission.delegate.PermissionDelegate

class NotificationPermissionDelegate(
    private val context: Context,
    private val mainActivityUseCase: MainActivityUseCase
) : PermissionDelegate {

    override fun getPermissionState(): PermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermissions(
                context,
                mainActivityUseCase.requireActivity(),
                postNotificationPermissions
            )
        } else {
            PermissionState.GRANTED
        }

    override fun providePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mainActivityUseCase.requireActivity().providePermissions(postNotificationPermissions) {
                throw IllegalStateException("Cannot provide permission: ${Permission.NOTIFICATION.name}")
            }
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.NOTIFICATION)
    }

    private val postNotificationPermissions: List<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyList()
        }
}
