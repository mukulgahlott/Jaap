package com.coretechies.jaap.utils

import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertAction
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.UIViewController

actual fun showToast(message: String, context: Any?) {
    val alertController = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertController.StyleAlert
    )
    alertController.addAction(UIAlertAction.actionWithTitle(
        title = "OK",
        style = UIAlertAction.StyleDefault,
        handler = null
    ))

    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(alertController, animated = true, completion = null)
}