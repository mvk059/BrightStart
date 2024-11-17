package fyi.manpreet.brightstart.platform.permission.service

import fyi.manpreet.brightstart.platform.permission.Permission
import fyi.manpreet.brightstart.platform.permission.PermissionState
import kotlinx.coroutines.flow.Flow

interface PermissionService {
    fun checkPermission(permission: Permission): PermissionState
    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
    suspend fun providePermission(permission: Permission)
    fun openSettingsPage(permission: Permission)
}
