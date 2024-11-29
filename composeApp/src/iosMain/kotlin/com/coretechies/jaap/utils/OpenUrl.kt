package com.coretechies.jaap.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.darwin.NSObject

actual fun openUrl(url: String, context : Any?) {
    val nsUrl = NSURL(string = url)
    nsUrl.let { url1 ->
        UIApplication.sharedApplication.openURL(
            url1,
            options = emptyMap<Any?, Any>(),
            completionHandler = { success ->
                if (!success) {
                    println("Failed to open URL: $url1")
                }
            }
        )
    }
}

actual fun openRate(context: Any?) {
    val nsUrl = NSURL(string = "https://apps.apple.com/us/app/digital-jaap/id6737768029")
    nsUrl.let { url ->
        UIApplication.sharedApplication.openURL(
            url,
            options = emptyMap<Any?, Any>(),
            completionHandler = { success ->
                if (!success) {
                    println("Failed to open URL: $url")
                }
            }
        )
    }
}
