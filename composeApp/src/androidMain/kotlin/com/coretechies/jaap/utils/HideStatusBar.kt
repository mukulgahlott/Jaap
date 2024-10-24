package com.coretechies.jaap.utils// androidMain (Android-specific) code
import androidx.core.view.WindowCompat
import android.app.Activity

fun hideSystemUI(activity: Activity) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
}
