package com.coretechies.jaap.shareApp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

actual fun shareApp(appLink: String, context: Any?) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, appLink)
        type = "text/plain"
    }

    val androidContext = context as? Context

    if (androidContext != null) {
        startActivity(androidContext, Intent.createChooser(intent, null), null)
    }
}