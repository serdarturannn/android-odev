package com.serdarturan.musicplayer

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import pub.devrel.easypermissions.EasyPermissions

class PermissionHelper(private val context: Context) {

    companion object {
        const val REQUEST_CODE_STORAGE_PERMS = 123
    }

    private val storagePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    fun hasStoragePermissions() = EasyPermissions.hasPermissions(context, *storagePermissions)

    fun requestStoragePermissions(activity: ComponentActivity) {
        EasyPermissions.requestPermissions(
            activity,
            "This application needs access to your storage to function properly.",
            REQUEST_CODE_STORAGE_PERMS,
            *storagePermissions
        )
    }
}
