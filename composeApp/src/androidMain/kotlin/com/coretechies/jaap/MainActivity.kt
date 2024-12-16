package com.coretechies.jaap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.coretechies.jaap.dataStore.createDataStore
import com.coretechies.jaap.dataStore.room.getDatabaseHelper
import com.coretechies.jaap.ui.App
import com.coretechies.jaap.ui.SplashScreen
import com.coretechies.jaap.utils.appContext
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        appContext = applicationContext

        val countingDao = getDatabaseHelper(applicationContext).countingDao()


        Firebase.initialize(this)

        setContent {
                App(context = LocalContext.current, prefs = remember {
                    createDataStore(applicationContext)
                }, countingDao = countingDao)
//                MobileAds.initialize(this)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}