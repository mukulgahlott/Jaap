package com.coretechies.jaap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.coretechies.jaap.dataStore.createDataStore
import com.coretechies.jaap.dataStore.room.getDatabaseHelper
import com.coretechies.jaap.screens.App
import com.coretechies.jaap.screens.MainScreen
import com.coretechies.jaap.ui.SplashScreen
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val countingDao = getDatabaseHelper(applicationContext).countingDao()

        Firebase.initialize(this)

        setContent {

            var isSplashScreenVisible by remember { mutableStateOf(true) } // State for splash screen visibility

            // Show splash screen
            if (isSplashScreenVisible) {
                SplashScreen {
                    isSplashScreenVisible = false
                }
            } else {

                MainScreen(context = LocalContext.current,prefs = remember {
                    createDataStore(applicationContext)
                }, countingDao = countingDao)
            }
            window.statusBarColor = 0xFFef9d54.toInt()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}