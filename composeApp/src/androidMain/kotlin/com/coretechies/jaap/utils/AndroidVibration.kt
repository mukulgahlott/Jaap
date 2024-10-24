// androidMain
package com.coretechies.jaap.utils

import android.content.Context
import android.os.Build
import android.os.Vibrator

actual fun triggerVibration(context: Any?, duration: Long) {
    val androidContext = context as? Context
    if (androidContext != null) {
        val vibrator = androidContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(android.os.VibrationEffect.createOneShot(duration, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(duration)
        }
    }
}