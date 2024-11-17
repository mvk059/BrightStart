package fyi.manpreet.brightstart.di

import fyi.manpreet.brightstart.platform.permission.Permission
import fyi.manpreet.brightstart.platform.permission.NotificationPermissionDelegate
import fyi.manpreet.brightstart.platform.permission.delegate.PermissionDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun providePermissionsModule() = module {
    single<PermissionDelegate>(named(Permission.NOTIFICATION.name)) {
        NotificationPermissionDelegate(context = androidContext(), mainActivityUseCase = get())
    }
}