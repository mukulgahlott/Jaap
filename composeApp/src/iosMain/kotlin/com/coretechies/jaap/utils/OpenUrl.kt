package com.coretechies.jaap.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String, context : Any?) {
    val nsUrl = NSURL(string = url)
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual fun openRate(context : Any?) {
    val nsUrl = NSURL(string = "https://apps.apple.com/us/app/digital-jaap/id6737768029")
    UIApplication.sharedApplication.openURL(nsUrl)
}
