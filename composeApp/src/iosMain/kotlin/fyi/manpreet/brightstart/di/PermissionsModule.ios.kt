package fyi.manpreet.brightstart.di

import fyi.manpreet.brightstart.platform.permission.NotificationPermissionDelegate
import org.koin.dsl.module
import fyi.manpreet.brightstart.platform.permission.Permission
import fyi.manpreet.brightstart.platform.permission.delegate.PermissionDelegate
import org.koin.core.qualifier.named

actual fun providePermissionsModule() = module {
    single<PermissionDelegate>(named(Permission.NOTIFICATION.name)) {
        NotificationPermissionDelegate()
    }
}