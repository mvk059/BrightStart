package fyi.manpreet.brightstart.platform.permission

import fyi.manpreet.brightstart.platform.permission.delegate.PermissionDelegate
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatus
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusEphemeral
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNAuthorizationStatusProvisional
import platform.UserNotifications.UNNotificationSettings
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.suspendCoroutine

class NotificationPermissionDelegate : PermissionDelegate {

    override suspend fun getPermissionState(): PermissionState {
        val currentCenter = UNUserNotificationCenter.currentNotificationCenter()
        val status = suspendCoroutine { continuation ->
            currentCenter.getNotificationSettingsWithCompletionHandler { settings ->
                continuation.resumeWith(
                    Result.success(
                        settings?.authorizationStatus ?: UNAuthorizationStatusNotDetermined
                    )
                )
            }
        }
        return when (status) {
            UNAuthorizationStatusAuthorized,
            UNAuthorizationStatusProvisional,
            UNAuthorizationStatusEphemeral -> PermissionState.GRANTED
            UNAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            UNAuthorizationStatusDenied -> PermissionState.DENIED
            else -> error("unknown push authorization status $status")
        }
    }

    override suspend fun providePermission() {
        val center = UNUserNotificationCenter.currentNotificationCenter()
        val status: UNAuthorizationStatus = suspendCoroutine { continuation ->
            center.getNotificationSettingsWithCompletionHandler { settings: UNNotificationSettings? ->
                continuation.resumeWith(
                    Result.success(
                        settings?.authorizationStatus ?: UNAuthorizationStatusNotDetermined
                    )
                )
            }
        }
        when (status) {
            UNAuthorizationStatusAuthorized -> return
            UNAuthorizationStatusNotDetermined -> {
                val isSuccess = suspendCoroutine { continuation ->
                    UNUserNotificationCenter.currentNotificationCenter()
                        .requestAuthorizationWithOptions(
                            UNAuthorizationOptionSound
                                .or(UNAuthorizationOptionAlert)
                                .or(UNAuthorizationOptionBadge), { isOk, error ->
                                if (isOk && error == null) {
                                    continuation.resumeWith(Result.success(true))
                                } else {
                                    continuation.resumeWith(Result.success(false))
                                }
                            }
                        )
                }
                if (isSuccess) {
                    providePermission()
                } else {
                    error("notifications permission failed")
                }
            }
        }
    }

    override fun openSettingPage() {
//        UIApplication.sharedApplication.openURL(
//            NSURL.URLWithString(UIApplication.sharedApplication.opensSettingsURLString)!!
//        )
    }
}
