// iosMain
package com.coretechies.jaap.utils

actual fun triggerVibration(context: Any?, duration: Long) {
        val feedback = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
        feedback.impactOccurred()
    }