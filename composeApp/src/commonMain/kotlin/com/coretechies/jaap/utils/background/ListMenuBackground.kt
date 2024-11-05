package com.coretechies.jaap.utils.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.coretechies.jaap.dataStore.DataStoreManager
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bg_dark
import japp.composeapp.generated.resources.bg_light
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
fun ListMenuBackground( prefs : DataStore<Preferences> ,content: @Composable () -> Unit) {

    // Shared Pref For Dark Mode
    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)
    // Shared Pref For Dark Mode
    val darkMode by dataStoreManager.darkMode.collectAsState(false)

    Box(
        modifier = Modifier.fillMaxSize().imePadding(),
    ) {
        // Background Image
        Column(
            modifier= Modifier.background(if(darkMode)Color.Black else Color.White).fillMaxSize()
        ){

        }

        // Content overlay
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            contentAlignment = Alignment.Center // Center content if needed
        ) {
            content()
        }
    }
}
