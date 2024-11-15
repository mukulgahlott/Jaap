package com.coretechies.jaap.shareApp

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIPasteboard
import platform.UIKit.UIViewController

actual fun shareApp(appLink: String, context: Any?) {
    val activityViewController = UIActivityViewController(activityItems = listOf("$appLink https://apps.apple.com/us/app/digital-jaap/id6737768029"), applicationActivities = null)
    val topController = UIApplication.sharedApplication.keyWindow?.rootViewController
    topController?.presentViewController(activityViewController, true, null)
}
