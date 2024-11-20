package com.coretechies.jaap.ui

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.coretechies.jaap.dataStore.createDataStore
import com.coretechies.jaap.dataStore.getDatabaseHelper

fun MainScreenView() = ComposeUIViewController {

    val prefs = remember { createDataStore() }
    val countingDao = getDatabaseHelper().countingDao()

    MainScreen(prefs = prefs , countingDao = countingDao, context = null)
    }

