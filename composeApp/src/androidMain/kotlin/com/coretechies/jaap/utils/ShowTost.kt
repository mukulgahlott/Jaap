package com.coretechies.jaap.utils

import android.content.Context
import android.widget.Toast

actual fun showToast(message: String , context : Any?) {
    var androidContext = context as? Context

    if (androidContext != null) {
        Toast.makeText(androidContext, message, Toast.LENGTH_SHORT).show()
    }
}