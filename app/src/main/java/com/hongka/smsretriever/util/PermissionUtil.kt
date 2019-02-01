package com.hongka.smsretriever.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

/**
 * Created by jusung.kim@sk.com on 2019/02/02
 */
class PermissionUtil {
    companion object {
        /**
         * Check that all given permissions have been granted by verifying that each entry in the
         * given array is of the value [PackageManager.PERMISSION_GRANTED].

         * @see Activity.onRequestPermissionsResult
         */
        fun verifyPermissions(grantResults: IntArray) =
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }

        /**
         * Returns true if the Activity has access to all given permissions.
         * Always returns true on platforms below M.

         * @see Activity.checkSelfPermission
         */
        fun hasSelfPermission(context: Context, permissions: Array<String>): Boolean {
            // Below Android M all permissions are granted at install time and are already available.
            if (!needToCheckPermission()) {
                return true
            }

            // Verify that all required permissions have been granted
            return permissions.all { checkSelfPermission(context, it) }
        }

        fun shouldShowRationale(activity: Activity, permissions: Array<String>) =
            permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }

        private fun needToCheckPermission() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        private fun checkSelfPermission(context: Context, permission: String): Boolean {
            // Below Android M all permissions are granted at install time and are already available.
            if (!needToCheckPermission()) {
                return true
            }

            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}