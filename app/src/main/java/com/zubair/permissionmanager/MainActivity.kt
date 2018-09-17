package com.zubair.permissionmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.zubair.permissionmanager.enums.PermissionEnum
import com.zubair.permissionmanager.interfaces.FullCallback

class MainActivity : AppCompatActivity(), FullCallback {

    //static variables
    private val REQUEST_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //permission test
        if (isStoragePermissionGranted()) {

        } else {
            requestStoragePermissions()
        }
    }

    //Permission Methods:---------------------------------------------------------------------------
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults)
    }

    override fun result(permissionsGranted: ArrayList<PermissionEnum>, permissionsDenied: ArrayList<PermissionEnum>, permissionsDeniedForever: ArrayList<PermissionEnum>, permissionsAsked: ArrayList<PermissionEnum>) {
        if (permissionsGranted.size == permissionsAsked.size) {
            //Do some action

        } else if (permissionsDeniedForever.size > 0) {
            //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
            showDialog(true)
        } else {
            showDialog(false)
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        var flag = false

        if (PermissionUtils.isGranted(this@MainActivity, PermissionEnum.WRITE_EXTERNAL_STORAGE) && PermissionUtils.isGranted(this@MainActivity, PermissionEnum.READ_EXTERNAL_STORAGE)) {
            flag = true
        }
        return flag
    }

    private fun requestStoragePermissions() {
        PermissionManager.Builder()
                .key(REQUEST_PERMISSIONS)
                .permission(PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.WRITE_EXTERNAL_STORAGE)
                .callback(this@MainActivity)
                .ask(this@MainActivity)
    }

    private fun showDialog(isNeverAskAgainChecked: Boolean) {
        AlertDialog.Builder(this@MainActivity)
                .setTitle("Permission needed")
                .setMessage(R.string.permission_confirmation)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->

                    if (!isNeverAskAgainChecked) {
                        requestStoragePermissions()
                    } else {
                        PermissionUtils.openApplicationSettings(this@MainActivity, R::class.java.getPackage().name)
                    }
                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
    }
}
