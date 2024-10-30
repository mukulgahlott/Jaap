// iosMain
package com.coretechies.jaap.utils

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

actual fun triggerVibration(context: Any?, duration: Long) {
    val feedback = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
    feedback.impactOccurred()
}