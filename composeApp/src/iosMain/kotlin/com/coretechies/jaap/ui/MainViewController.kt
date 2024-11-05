package com.coretechies.jaap.ui

import HomeScreen
import MenuScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.coretechies.jaap.dataStore.createDataStore
import com.coretechies.jaap.dataStore.getDatabaseHelper
import com.coretechies.jaap.room.counter.CountingDetails
import com.coretechies.jaap.screens.ListScreen
import com.coretechies.jaap.screens.MainScreen

fun MainScreenView() = ComposeUIViewController {

    val prefs = remember { createDataStore() }
    val countingDao = getDatabaseHelper().countingDao()

    MainScreen(prefs = prefs , countingDao = countingDao, context = null)
    }

