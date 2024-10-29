package com.coretechies.jaap.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.content.ActivityNotFoundException


actual fun openUrl(url: String, context: Any?) {
    val androidContext = context as? Context
    if (androidContext!=null) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            androidContext.startActivity(intent)
        }
}