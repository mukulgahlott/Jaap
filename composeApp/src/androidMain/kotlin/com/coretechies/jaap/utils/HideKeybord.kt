package com.coretechies.jaap.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.platform.LocalContext

// Implement the actual function for Android
actual fun hideKeyboard(context: Any?) {
    if (context is Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = (context as? android.app.Activity)?.currentFocus
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
