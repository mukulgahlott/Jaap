package com.coretechies.jaap.utils


import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

    // Pass applicationContext from Android MainActivity or Application
    lateinit var appContext: Context

    actual fun getDeviceId(): String {
        return Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID)
    }

    actual suspend fun getFcmToken(): String {
        return FirebaseMessaging.getInstance().token.await()
    }

