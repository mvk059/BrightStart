package fyi.manpreet.brightstart.platform.permission.delegate

import fyi.manpreet.brightstart.platform.permission.PermissionState

interface PermissionDelegate {
    suspend fun getPermissionState(): PermissionState
    suspend fun providePermission()
    fun openSettingPage()
}
