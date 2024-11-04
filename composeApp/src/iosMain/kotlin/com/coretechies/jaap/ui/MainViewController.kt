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

fun MainScreenView(tab: String) = ComposeUIViewController {

    var countingData by remember { mutableStateOf<CountingDetails?>(null) }
    val prefs = remember { createDataStore() }
    val countingDao = getDatabaseHelper().countingDao()
    var isSplashScreenVisible by remember { mutableStateOf(true) } // State for splash screen visibility

    // Show splash screen
    if (isSplashScreenVisible) {
        SplashScreen {
            isSplashScreenVisible = false
        }
    } else {
        // Use mutableStateOf to keep track of the current tab
        var currentTab by remember { mutableStateOf(tab) }

        // Function to determine which screen to show based on the current tab
        @Composable
        fun CurrentScreen() {
            when (currentTab) {
                "home" -> HomeScreen(
                    context = null,
                    prefs = prefs,
                    countingDao = countingDao,
                    countingDetails = countingData,
                    onDiscontinue = {
                        countingData = null
                    }
                )

                "list" -> ListScreen(prefs = prefs, countingDao = countingDao) { data ->
                    countingData = data
                    currentTab = "home"
                }

                "menu" -> MenuScreen(prefs = prefs, context = null)
                else -> HomeScreen(
                    context = null,
                    prefs = prefs,
                    countingDao = countingDao,
                    countingDetails = countingData,
                    onDiscontinue = {
                        countingData = null
                    }
                )
            }
        }

        CurrentScreen()
    }
}
