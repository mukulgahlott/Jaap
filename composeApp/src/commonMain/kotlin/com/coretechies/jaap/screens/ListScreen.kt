package com.coretechies.jaap.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.coretechies.jaap.cardview.CardViewJaap
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import customButtons
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.list
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.volume
import kotlinx.coroutines.flow.map
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

    val darkMode by prefs.data.map {
        val darkModeKey = booleanPreferencesKey("DarkMode")
        it[darkModeKey] ?: false
    }.collectAsState(false)

    val volumeEnabled by prefs.data.map {
        val volumeKey = booleanPreferencesKey("BeepSoundEnabled")
        it[volumeKey] ?: false
    }.collectAsState(false)

    val name by prefs.data.map {
        val nameKey = stringPreferencesKey("name")
        it[nameKey] ?: "none"
    }.collectAsState("none")


    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fixed Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            customButtons(volumeBackgroundColor,
                Res.drawable.volume,
                volumeEnabled,
                darkMode = darkMode,
                onClick = {
                    scope.launch {
                        prefs.edit { dataStore ->
                            val volumeKey = booleanPreferencesKey("BeepSoundEnabled")
                            dataStore[volumeKey] = !volumeEnabled
                        }
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
                        prefs.edit { dataStore ->
                            val darkModeKey = booleanPreferencesKey("DarkMode")
                            dataStore[darkModeKey] = !darkMode
                        }
                    }
                })
        }

        // LazyColumn to display the list of Jaap cards
        LazyColumn(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (countingData.isNotEmpty()) {
                items(countingData) { item ->
                    CardViewJaap(item, darkMode, onDelete = {
                        scope.launch {
                            countingDao.delete(item)
                        }
                    }, onContinue = {
                        scope.launch {
                            prefs.edit { dataStore ->
                                val counterKey = intPreferencesKey("counter")
                                dataStore[counterKey] = item.totalCount
                            }
                        }
                        onRoute(item)
                    })
                }
            }
        }
    }
}

