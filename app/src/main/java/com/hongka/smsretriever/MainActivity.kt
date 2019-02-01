package com.hongka.smsretriever

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hongka.smsretriever.util.PermissionUtil

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSIONS_REQUEST_SMS = 0
        private const val TAG = "MainActivity"
    }

    private val SMSPermissions = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_PHONE_STATE
    )
    private val hasSMSPermission get() = PermissionUtil.hasSelfPermission(this, SMSPermissions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!hasSMSPermission) {
            ActivityCompat.requestPermissions(this, SMSPermissions, PERMISSIONS_REQUEST_SMS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_SMS -> {
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    Log.i(TAG, "권한 허용")
                } else {
                    Log.i(TAG, "권한 거절")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
