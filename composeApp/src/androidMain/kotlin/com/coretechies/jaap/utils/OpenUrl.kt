package com.coretechies.jaap.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast


actual fun openUrl(url: String, context: Any?) {
    val androidContext = context as? Context
    if (androidContext != null) {
    try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            androidContext.startActivity(intent)
        }

    catch (_: Exception) {
        Toast.makeText(androidContext, "This Device can't open this link", Toast.LENGTH_SHORT).show()

    }
    }
}


actual fun openRate(context: Any?) {
    val androidContext = context as? Context
    if (androidContext != null) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.coretechies.jaap"))
            androidContext.startActivity(intent)
        }

        catch (_: Exception) {
            Toast.makeText(androidContext, "This Device can't open this link", Toast.LENGTH_SHORT).show()

        }
    }
}