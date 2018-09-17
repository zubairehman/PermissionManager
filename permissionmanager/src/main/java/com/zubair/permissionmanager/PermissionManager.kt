/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Zubair Rehman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zubair.permissionmanager

import android.content.pm.PackageManager
import android.os.Build
import android.support.v13.app.FragmentCompat
import android.support.v4.app.ActivityCompat
import com.zubair.permissionmanager.constants.PermissionConstant
import com.zubair.permissionmanager.enums.PermissionEnum
import com.zubair.permissionmanager.interfaces.AskAgainCallback
import com.zubair.permissionmanager.interfaces.FullCallback
import com.zubair.permissionmanager.interfaces.SimpleCallback
import com.zubair.permissionmanager.interfaces.SmartCallback
import java.util.*

/**
 * Created by Zubair.Rehman on 01-Nov-2017.
 */
class PermissionManager {

    private var fullCallback: FullCallback? = null
    private var simpleCallback: SimpleCallback? = null
    private var askAgainCallback: AskAgainCallback? = null
    private var smartCallback: SmartCallback? = null

    private var askAgain = false

    private var permissions: ArrayList<PermissionEnum>? = null
    private var permissionsGranted: ArrayList<PermissionEnum>? = null
    private var permissionsDenied: ArrayList<PermissionEnum>? = null
    private var permissionsDeniedForever: ArrayList<PermissionEnum>? = null
    private var permissionToAsk: ArrayList<PermissionEnum>? = null

    private var key = PermissionConstant.KEY_PERMISSION

    /**
     * @param permissions an array of permission that you need to ask
     * @return current instance
     */
    fun permissions(permissions: ArrayList<PermissionEnum>): PermissionManager {
        this.permissions = ArrayList()
        this.permissions!!.addAll(permissions)
        return this
    }

    /**
     * @param permission permission you need to ask
     * @return current instance
     */
    fun permission(permission: PermissionEnum): PermissionManager {
        this.permissions = ArrayList()
        this.permissions!!.add(permission)
        return this
    }

    /**
     * @param permissions permission you need to ask
     * @return current instance
     */
    fun permission(vararg permissions: PermissionEnum): PermissionManager {
        this.permissions = ArrayList()
        Collections.addAll(this.permissions!!, *permissions)
        return this
    }

    /**
     * @param askAgain ask again when permission not granted
     * @return current instance
     */
    fun askAgain(askAgain: Boolean): PermissionManager {
        this.askAgain = askAgain
        return this
    }

    /**
     * @param fullCallback set fullCallback for the request
     * @return current instance
     */
    fun callback(fullCallback: FullCallback): PermissionManager {
        this.simpleCallback = null
        this.smartCallback = null
        this.fullCallback = fullCallback
        return this
    }

    /**
     * @param simpleCallback set simpleCallback for the request
     * @return current instance
     */
    fun callback(simpleCallback: SimpleCallback): PermissionManager {
        this.fullCallback = null
        this.smartCallback = null
        this.simpleCallback = simpleCallback
        return this
    }

    /**
     * @param smartCallback set smartCallback for the request
     * @return current instance
     */
    fun callback(smartCallback: SmartCallback): PermissionManager {
        this.fullCallback = null
        this.simpleCallback = null
        this.smartCallback = smartCallback
        return this
    }

    /**
     * @param askAgainCallback set askAgainCallback for the request
     * @return current instance
     */
    fun askAgainCallback(askAgainCallback: AskAgainCallback): PermissionManager {
        this.askAgainCallback = askAgainCallback
        return this
    }

    /**
     * @param key set a custom request code
     * @return current instance
     */
    fun key(key: Int): PermissionManager {
        this.key = key
        return this
    }

    /**
     * @param activity target activity
     * just start all permission manager
     */
    fun ask(activity: android.app.Activity) {
        ask(activity, null, null)
    }

    /**
     * @param v4fragment target v4 fragment
     * just start all permission manager
     */
    fun ask(v4fragment: android.support.v4.app.Fragment) {
        ask(null, v4fragment, null)
    }

    /**
     * @param fragment target fragment
     * just start all permission manager
     */
    fun ask(fragment: android.app.Fragment) {
        ask(null, null, fragment)
    }

    private fun ask(activity: android.app.Activity?, v4fragment: android.support.v4.app.Fragment?, fragment: android.app.Fragment?) {
        initArray()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionToAsk = permissionToAsk(activity, v4fragment, fragment)
            if (permissionToAsk.size == 0) {
                showResult()
            } else {
                if (activity != null) {
                    ActivityCompat.requestPermissions(activity, permissionToAsk, key)
                } else if (fragment != null) {
                    FragmentCompat.requestPermissions(fragment, permissionToAsk, key)
                } else v4fragment?.requestPermissions(permissionToAsk, key)
            }
        } else {
            permissionsGranted!!.addAll(this.permissions!!)
            showResult()
        }
    }

    /**
     * @return permission that you really need to ask
     */
    private fun permissionToAsk(activity: android.app.Activity?, v4fragment: android.support.v4.app.Fragment?, fragment: android.app.Fragment?): Array<String> {
        val permissionToAsk = ArrayList<String>()
        for (permission in permissions!!) {
            var isGranted = false
            if (activity != null) {
                isGranted = PermissionUtils.isGranted(activity, permission)
            } else if (fragment != null) {
                isGranted = PermissionUtils.isGranted(fragment.activity, permission)
            } else if (v4fragment != null) {
                isGranted = PermissionUtils.isGranted(v4fragment.activity!!, permission)
            }
            if (!isGranted) {
                permissionToAsk.add(permission.toString())
            } else {
                permissionsGranted!!.add(permission)
            }
        }
        return permissionToAsk.toTypedArray()
    }

    /**
     * init permissions ArrayList
     */
    private fun initArray() {
        this.permissionsGranted = ArrayList()
        this.permissionsDenied = ArrayList()
        this.permissionsDeniedForever = ArrayList()
        this.permissionToAsk = ArrayList()
    }

    /**
     * check if one of three types of callback are not null and pass data
     */
    private fun showResult() {
        if (simpleCallback != null)
            simpleCallback!!.result(permissionToAsk!!.size == 0 || permissionToAsk!!.size == permissionsGranted!!.size)
        if (fullCallback != null)
            fullCallback!!.result(permissionsGranted!!, permissionsDenied!!, permissionsDeniedForever!!, permissions!!)
        if (smartCallback != null)
            smartCallback!!.result(permissionToAsk!!.size == 0 || permissionToAsk!!.size == permissionsGranted!!.size, !permissionsDeniedForever!!.isEmpty())
        instance = null
    }

    companion object {

        private var instance: PermissionManager? = null

        /**
         * @return current instance
         */
        fun Builder(): PermissionManager {
            if (instance == null) {
                instance = PermissionManager()
            }
            return instance as PermissionManager
        }

        /**
         * @param activity     target activity
         * @param requestCode  requestCode
         * @param permissions  permissions
         * @param grantResults grantResults
         */
        fun handleResult(activity: android.app.Activity, requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            handleResult(activity, null, null, requestCode, permissions, grantResults)
        }

        /**
         * @param v4fragment   target v4 fragment
         * @param requestCode  requestCode
         * @param permissions  permissions
         * @param grantResults grantResults
         */
        fun handleResult(v4fragment: android.support.v4.app.Fragment, requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            handleResult(null, v4fragment, null, requestCode, permissions, grantResults)
        }

        /**
         * @param fragment     target fragment
         * @param requestCode  requestCode
         * @param permissions  permissions
         * @param grantResults grantResults
         */
        fun handleResult(fragment: android.app.Fragment, requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            handleResult(null, null, fragment, requestCode, permissions, grantResults)
        }

        private fun handleResult(activity: android.app.Activity?, v4fragment: android.support.v4.app.Fragment?, fragment: android.app.Fragment?, requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            if (instance == null) return
            if (requestCode == instance!!.key) {
                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        instance!!.permissionsGranted!!.add(PermissionEnum.fromManifestPermission(permissions[i]))
                    } else {
                        var permissionsDeniedForever = false
                        if (activity != null) {
                            permissionsDeniedForever = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])
                        } else if (fragment != null) {
                            permissionsDeniedForever = FragmentCompat.shouldShowRequestPermissionRationale(fragment, permissions[i])
                        } else if (v4fragment != null) {
                            permissionsDeniedForever = v4fragment.shouldShowRequestPermissionRationale(permissions[i])
                        }
                        if (!permissionsDeniedForever) {
                            instance!!.permissionsDeniedForever!!.add(PermissionEnum.fromManifestPermission(permissions[i]))
                        }
                        instance!!.permissionsDenied!!.add(PermissionEnum.fromManifestPermission(permissions[i]))
                        instance!!.permissionToAsk!!.add(PermissionEnum.fromManifestPermission(permissions[i]))
                    }
                }
                if (instance!!.permissionToAsk!!.size != 0 && instance!!.askAgain) {
                    instance!!.askAgain = false
                    if (instance!!.askAgainCallback != null && instance!!.permissionsDeniedForever!!.size != instance!!.permissionsDenied!!.size) {
                        instance!!.askAgainCallback!!.showRequestPermission(object : AskAgainCallback.UserResponse {
                            override fun result(askAgain: Boolean) {
                                if (askAgain) {
                                    instance!!.ask(activity, v4fragment, fragment)
                                } else {
                                    instance!!.showResult()
                                }
                            }
                        })
                    } else {
                        instance!!.ask(activity, v4fragment, fragment)
                    }
                } else {
                    instance!!.showResult()
                }
            }
        }
    }

}