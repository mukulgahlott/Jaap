package com.coretechies.jaap.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.coretechies.jaap.cardview.CardViewJaap
import com.coretechies.jaap.dataStore.DataStoreManager
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import com.coretechies.jaap.utils.background.ListMenuBackground
import customButtons
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.list
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.noRecords
import japp.composeapp.generated.resources.volume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

data class JaapData(val count: String, val title: String, val time: String)

@Composable
fun ListScreen(
    prefs: DataStore<Preferences>,
    countingDao: CountingDao,
    onRoute: (countingData: CountingDetails) -> Unit
) {
    val countingData by countingDao.getAllCountingDetails().collectAsState(initial = emptyList())

    val moonBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val volumeBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }

    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)

    // Shared Pref For Dark Mode
    val darkMode by dataStoreManager.darkMode.collectAsState(false)

    // Shared Pref For Beep Tone Sound
    val beepSoundEnabled by dataStoreManager.beepSoundEnabled.collectAsState(false)

    ListMenuBackground(prefs = prefs) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fixed Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                customButtons(volumeBackgroundColor,
                    Res.drawable.volume,
                    beepSoundEnabled,
                    darkMode = darkMode,
                    onClick = {
                        scope.launch {
                            dataStoreManager.setBeepSoundEnabled(!beepSoundEnabled)
                        }
                    })

                Text(
                    modifier = Modifier.wrapContentHeight(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkMode) Color.White else Color(0XFF87490c),
                    text = stringResource(Res.string.list),
                    textAlign = TextAlign.Center
                )

                customButtons(moonBackgroundColor,
                    Res.drawable.moon_stars,
                    darkMode,
                    darkMode = darkMode,
                    onClick = {
                        scope.launch {
                            dataStoreManager.setDarkMode(!darkMode)
                        }
                    })
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (countingData.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(countingData.reversed()) { item ->
                            CardViewJaap(
                                item,
                                darkMode,
                                onDelete = {
                                    scope.launch {
                                        countingDao.delete(item)
                                    }
                                },
                                onContinue = {
                                    scope.launch(Dispatchers.IO) {
                                        dataStoreManager.setCounter(item.totalCount)
                                        dataStoreManager.setId(item.id)
                                        dataStoreManager.setTitle(item.countTitle)
                                        dataStoreManager.setTarget(item.target.toString())
                                        dataStoreManager.setCounter(item.totalCount % item.target )
                                        dataStoreManager.setMala(item.totalCount / item.target)

                                        launch(Dispatchers.Main) {
                                            onRoute(item)
                                            dataStoreManager.setCounter(item.totalCount % item.target )
                                            dataStoreManager.setId(item.id)
                                            dataStoreManager.setTarget(item.target.toString())
                                            dataStoreManager.setMala(item.totalCount / item.target)
                                        }
                                    }
                                }
                            )
                        }
                        // Add a Spacer at the bottom
                        item {
                            Spacer(modifier = Modifier.height(90.dp))
                        }
                    }
                } else {
                    Text(
                        text = stringResource(Res.string.noRecords),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (darkMode) Color.White else Color.Black,
                        modifier = Modifier
                            .align(Alignment.Center) // Center the text
                            .padding(16.dp) // Optional padding
                    )
                }
            }
        }
    }
}