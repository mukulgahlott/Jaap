package com.coretechies.jaap.utils

import platform.UIKit.UIDevice
import platform.Foundation.NSUUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual fun getDeviceId(): String {
    return UIDevice.currentDevice.identifierForVendor?.UUIDString ?: NSUUID().UUIDString()
}


actual suspend fun getFcmToken(): String {
    return suspendCoroutine { continuation ->
//        FcmTokenProvider.getToken { token ->
            continuation.resume("token") // Resume with the token value
        }
    }
//}
