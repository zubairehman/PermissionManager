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

package com.zubair.permissionmanager.enums

import android.Manifest
import android.annotation.SuppressLint

/**
 * Created by Zubair.Rehman on 01-Nov-2017.
 */
@SuppressLint("InlinedApi")
enum class PermissionEnum private constructor(private val permission: String) {

    BODY_SENSORS(Manifest.permission.BODY_SENSORS),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
    CALL_PHONE(Manifest.permission.CALL_PHONE),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),
    USE_SIP(Manifest.permission.USE_SIP),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS),
    CAMERA(Manifest.permission.CAMERA),
    SEND_SMS(Manifest.permission.SEND_SMS),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
    READ_SMS(Manifest.permission.READ_SMS),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),

    GROUP_CALENDAR(Manifest.permission_group.CALENDAR),
    GROUP_CAMERA(Manifest.permission_group.CAMERA),
    GROUP_CONTACTS(Manifest.permission_group.CONTACTS),
    GROUP_LOCATION(Manifest.permission_group.LOCATION),
    GROUP_MICROPHONE(Manifest.permission_group.MICROPHONE),
    GROUP_PHONE(Manifest.permission_group.PHONE),
    GROUP_SENSORS(Manifest.permission_group.SENSORS),
    GROUP_SMS(Manifest.permission_group.SMS),
    GROUP_STORAGE(Manifest.permission_group.STORAGE),

    NULL("");

    override fun toString(): String {
        return permission
    }

    companion object {

        fun fromManifestPermission(value: String): PermissionEnum {
            for (permissionEnum in PermissionEnum.values()) {
                if (value.equals(permissionEnum.permission, ignoreCase = true)) {
                    return permissionEnum
                }
            }
            return NULL
        }
    }

}
