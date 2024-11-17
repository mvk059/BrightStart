package fyi.manpreet.brightstart.platform.permission.delegate

import fyi.manpreet.brightstart.platform.permission.PermissionState

interface PermissionDelegate {
    fun getPermissionState(): PermissionState
    fun providePermission()
    fun openSettingPage()
}
