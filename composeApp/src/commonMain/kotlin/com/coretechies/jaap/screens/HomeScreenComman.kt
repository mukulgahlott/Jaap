import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import com.coretechies.jaap.utils.playBeep
import com.coretechies.jaap.utils.showToast
import com.coretechies.jaap.utils.triggerVibration
import com.example.jetpackCompose.ui.theme.Orange
import japp.composeapp.generated.resources.DS_DIGI
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.device_1
import japp.composeapp.generated.resources.ic_device_dark
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.palette
import japp.composeapp.generated.resources.save
import japp.composeapp.generated.resources.vibrate
import japp.composeapp.generated.resources.volume
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    context: Any?,
    prefs: DataStore<Preferences>,
    countingDao: CountingDao,
    countingDetails: CountingDetails?,
    onDiscontinue: () -> Unit
) {
    val volumeBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val vibrationBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val theamBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val darkModBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }

    var showSaveBottomSheet by remember { mutableStateOf(false) }
    var showResetBottomSheet by remember { mutableStateOf(false) }
    // counter state
    var defaultCounterCount by remember { mutableStateOf(0) }

    // Shad Pref Variables
    val counter by prefs
        .data
        .map {
            val counterKey = intPreferencesKey("counter")
            it[counterKey] ?: 0
        }.collectAsState(0)

    // Shared Pref For Dark Mode
    val darkMode by prefs
        .data
        .map {
            val darkModeKey = booleanPreferencesKey("DarkMode")
            it[darkModeKey] ?: false
        }.collectAsState(false)

    // Shared Pref For Beep Tone Sound
    val beepSoundEnabled by prefs
        .data
        .map {
            val beepSoundKey = booleanPreferencesKey("BeepSoundEnabled")
            it[beepSoundKey] ?: true
        }.collectAsState(true)

    // Shared Pref For Vibration
    val vibrationEnabled by prefs
        .data
        .map {
            val vibrationKey = booleanPreferencesKey("VibrationEnabled")
            it[vibrationKey] ?: true
        }.collectAsState(true)

    // For Coroutine Scope
    val scope = rememberCoroutineScope()

    FullScreenBackground(prefs) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (darkMode) Color.White else Color(0XFF87490c),
                text = "Digital Mala Jaap",
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                customButtons(volumeBackgroundColor,
                    Res.drawable.volume,
                    beepSoundEnabled,
                    darkMode = darkMode,
                    onClick = {
                        scope.launch {
                            prefs.edit { dataStore ->
                                val beepSoundKey = booleanPreferencesKey("BeepSoundEnabled")
                                dataStore[beepSoundKey] = !beepSoundEnabled
                            }
                        }
                    }
                )

                customButtons(vibrationBackgroundColor,
                    Res.drawable.vibrate,
                    vibrationEnabled,
                    darkMode = darkMode,
                    onClick = {
                        scope.launch {
                            prefs.edit { dataStore ->
                                val vibrationKey = booleanPreferencesKey("VibrationEnabled")
                                dataStore[vibrationKey] = !vibrationEnabled
                            }
                        }
                    })

                customButtons(theamBackgroundColor,
                    Res.drawable.palette,
                    false,
                    darkMode = darkMode,
                    onClick = {})

                customButtons(darkModBackgroundColor,
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

            Box(
                modifier = Modifier.wrapContentSize().padding(top = 50.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Image(
                            painter = painterResource(if (darkMode) Res.drawable.ic_device_dark else Res.drawable.device_1),
                            contentDescription = "device",
                            modifier = Modifier.size(350.dp)

                        )

                        Text(
                            text = if (counter == 0) "0000" else "$counter",
                            modifier = Modifier.padding(top = 70.dp),
                            fontSize = 70.sp,
                            fontFamily = FontFamily(
                                Font(Res.font.DS_DIGI)
                            ),
                            textAlign = TextAlign.End,
                        )

                        Button(
                            onClick = {
                                if (vibrationEnabled) triggerVibration(context, 100)
                                if (beepSoundEnabled) playBeep(context)
                                if (defaultCounterCount <= 9999)
                                    defaultCounterCount++
                                scope.launch {
                                    prefs.edit { dataStore ->
                                        val counterKey = intPreferencesKey("counter")
                                        dataStore[counterKey] = counter + 1
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .padding(top = 170.dp)
                                .size(width = 120.dp, height = 120.dp)
                                .clip(RoundedCornerShape(130.dp)),
                        ) {}

                        // Counter Reset Button
                        Button(
                            onClick = {
                                showResetBottomSheet = true
                                showSaveBottomSheet = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .padding(top = 148.dp, start = 150.dp)
                                .wrapContentSize(),
                        ) {
                        }
                    }

                    Button(
                        onClick = {
                            showSaveBottomSheet = true

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if(darkMode)Color.Black else Orange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 28.dp).height(60.dp)
                            .fillMaxWidth(fraction = 0.7f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.save),
                            modifier = Modifier.padding(8.dp),
                            fontSize = 22.sp,
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
            SaveBottomSheet(
                totalCount = counter,
                darkMode = darkMode,
                countingDao = countingDao,
                countingDetails = countingDetails,
                onDismiss = { showSaveBottomSheet = false },
                onSave = {
                    scope.launch {
                        prefs.edit { dataStore ->
                            val counterKey = intPreferencesKey("counter")
                            dataStore[counterKey] = 0
                        }
                    }
                    onDiscontinue()
                    showSaveBottomSheet = false
                },
                showBottomSheet = showSaveBottomSheet,

                onFail = {
                    showToast("Please Enter Name ", context)
                }
            )

        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
            resetBottomSheet(
                onDismiss = { showResetBottomSheet = false },
                darkMode = darkMode,
                onReset = {
                    showResetBottomSheet = false
                    scope.launch {
                        prefs.edit { dataStore ->
                            val counterKey = intPreferencesKey("counter")
                            dataStore[counterKey] = 0
                        }
                    }
                    onDiscontinue()
                },
                showBottomSheet = showResetBottomSheet
            )

        }

    }
}

