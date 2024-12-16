package com.coretechies.jaap.ui

import AppScreen
import HomeScreen
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coretechies.jaap.dataStore.DataStoreManager
import com.coretechies.jaap.localization.Language
import com.coretechies.jaap.room.counter.CountingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    context: Any?, prefs: DataStore<Preferences>, countingDao: CountingDao
) {

    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)
    val showLanguageSelectScreen by dataStoreManager.languageScreenEnabled.collectAsState(false)

    var isSplashScreenVisible by remember { mutableStateOf(true) } // State for splash screen visibility

    // Show splash screen
    if (isSplashScreenVisible) {
        SplashScreen {
            if (showLanguageSelectScreen) {
            }
            isSplashScreenVisible = false
        }
    }
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = if (showLanguageSelectScreen)AppScreen.ChooseLanguageScreen.route else AppScreen.MainScreen.route
    ) {
        composable(AppScreen.MainScreen.route) {
            MainScreen(
                context = context,
                prefs = prefs,
                countingDao = countingDao,
                navController = navController
            )
        }
        composable(AppScreen.ChooseLanguageScreen.route) {
            ChooseLanguageScreen(onNextClick = { language ->
                dataStoreManager.setLanguage(if (language == "English") Language.English.isoFormat else Language.Hindi.isoFormat);
            },
            navController = navController)
        }
        composable(AppScreen.WalkThroughScreen.route) {
            WalkThrough(onNext = {

            }, navController = navController)
        }
        composable(AppScreen.SignIn.route) { RegistrationScreen( navController ) }
        composable(AppScreen.LogIn.route) { LoginScreen(onForgotPasswordClick = {}, onRegisterClick = {},onContinueWithoutLogin ={} ,navController= navController) }
        composable(AppScreen.CongratulationScreen.route) { CongratsScreen(onContinueClick = {} , navController = navController) }
//        composable(AppScreen.SearchScreen.route) { SettingsScreen(navController) }
    }

}