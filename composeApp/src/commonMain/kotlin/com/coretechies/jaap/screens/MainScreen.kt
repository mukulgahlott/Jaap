package com.coretechies.jaap.screens

import HomeScreen
import MenuScreen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import com.coretechies.jaap.utils.AppConstants
import com.example.jetpackCompose.ui.theme.Orange
import com.example.jetpackCompose.ui.theme.gray
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.*
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    prefs: DataStore<Preferences>, countingDao: CountingDao,
    context: Any?
) {
    MaterialTheme {
        var selectedTab by remember { mutableStateOf(0) }
        // State for splash screen visibility
        val tabs = listOf(AppConstants.HOME, AppConstants.LIST, AppConstants.MENU)

        // Define the Bottom Tab Icon size once
        val iconModifier = Modifier.size(width = 28.dp, height = 24.dp)

        // Bottom Tab Colors
        val bottomBarColor = Color.White
        val bottomBarDarkColor = Color.Black
        val activeBottomTabIconColor = Orange
        val inActiveBottomTabIconColor = gray

        // State for DataFromList
        var countingDetailsObject by remember { mutableStateOf<CountingDetails?>(null) }

        // Shared Pref For Dark Mode
        val DarkMode by prefs
            .data
            .map {
                val darkModeKey = booleanPreferencesKey("DarkMode")
                it[darkModeKey] ?: false
            }.collectAsState(false)

        // Show splash screen

        Scaffold(
            bottomBar = {
                BottomNavigation( modifier = Modifier.wrapContentHeight(),
                    backgroundColor = if (!DarkMode) bottomBarColor else bottomBarDarkColor
                ) {
                    tabs.forEachIndexed { index, tab ->
                        val isActive = selectedTab == index // Determine if tab is active
                        val currentIcon = when (tab) {
                            AppConstants.HOME -> painterResource(Res.drawable.home)
                            AppConstants.LIST -> painterResource(Res.drawable.list)
                            AppConstants.MENU -> painterResource(Res.drawable.menu)
                            else -> throw IllegalArgumentException("Unknown tab: $tab")
                        }
                        println("Selected Tab: $tab, Active: $isActive") // Debug output

                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    currentIcon,
                                    contentDescription = tab,
                                    modifier = iconModifier,
                                    tint = if (isActive) activeBottomTabIconColor else inActiveBottomTabIconColor
                                )
                            },
                            label = {
                                Text(
                                    tab,
                                    color = if (isActive) activeBottomTabIconColor else inActiveBottomTabIconColor
                                )
                            },
                            selected = isActive,
                            onClick = { selectedTab = index },
                        )
                    }
                }
            }
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn() with fadeOut()
                }
            ) { targetState ->
                when (targetState) {
                    0 -> HomeScreen(
                        context,
                        prefs,
                        countingDao,
                        countingDetailsObject,
                        onDiscontinue = {
                            countingDetailsObject = null
                        }
                    )

                    1 -> ListScreen(prefs, countingDao) { countObject ->
                        countingDetailsObject = countObject
                        selectedTab = 0
                    }

                    2 -> MenuScreen(prefs = prefs, context = context)
                }
            }
        }
    }
}