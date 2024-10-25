package com.coretechies.jaap.ui

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.coretechies.jaap.dataStore.createDataStore
import com.coretechies.jaap.screens.App


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(prefs = remember {
                createDataStore(applicationContext)} )
        }
        window.statusBarColor = 0xFFef9d54.toInt()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}