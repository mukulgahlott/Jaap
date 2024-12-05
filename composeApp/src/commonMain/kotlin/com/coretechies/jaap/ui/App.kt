package com.coretechies.jaap.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.coretechies.jaap.dataStore.DataStoreManager
import com.coretechies.jaap.localization.Language
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(context: Any?,
        prefs: DataStore<Preferences>,
        countingDao: CountingDao ) {

    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)
    val showLanguageSelectScreen by dataStoreManager.languageScreenEnabled.collectAsState(false)

    var isSplashScreenVisible by remember { mutableStateOf(true) } // State for splash screen visibility

    // Show splash screen
    if (isSplashScreenVisible) {
        SplashScreen {
            if (showLanguageSelectScreen){}
            isSplashScreenVisible = false
        }
    }
   else if (showLanguageSelectScreen){
        ChooseLanguageScreen(onNextClick = { language ->
                 dataStoreManager.setLanguage(if (language=="English") Language.English.isoFormat else Language.Hindi.isoFormat);
                dataStoreManager.setLanguageScreenEnabled(false)
        })
    }
    else{
        MainScreen(context = context, prefs = prefs, countingDao = countingDao)
    }

}