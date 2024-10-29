package com.coretechies.jaap.ui

import MainScreen
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.coretechies.jaap.dataStore.createDataStore
import com.coretechies.jaap.dataStore.room.getDatabaseHelper
import com.coretechies.jaap.screens.App
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val countingDao = getDatabaseHelper(applicationContext).countingDao()

        Firebase.initialize(this)

        setContent {
            MainScreen(prefs = remember {
                createDataStore(applicationContext)
                } , countingDao )
        }
        window.statusBarColor = 0xFFef9d54.toInt()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}