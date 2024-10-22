package com.coretechies.jaap// androidMain (Android-specific) code
import androidx.core.view.WindowCompat
import android.app.Activity

fun hideSystemUI(activity: Activity) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
}
