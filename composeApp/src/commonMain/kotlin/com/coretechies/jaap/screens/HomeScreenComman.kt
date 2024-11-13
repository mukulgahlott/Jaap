import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.coretechies.jaap.dataStore.DataStoreManager
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import com.coretechies.jaap.utils.hideKeyboard
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
import japp.composeapp.generated.resources.ic_target
import japp.composeapp.generated.resources.ic_mala
import japp.composeapp.generated.resources.vibrate
import japp.composeapp.generated.resources.volume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    val themBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val darkModBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }

    var showSaveBottomSheet by remember { mutableStateOf(false) }
    var showResetBottomSheet by remember { mutableStateOf(false) }

    // For Coroutine Scope
    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)

    // Shad Pref Counter
    val counter by dataStoreManager.counter.collectAsState(initial = 0)

    // Shad Pref Id
    val id by dataStoreManager.id.collectAsState(initial = 0)

    // Shad Pref for Target
    val target by dataStoreManager.target.collectAsState(initial = 108)

    // Shad Pref for Mala
    val mala by dataStoreManager.mala.collectAsState(initial = 0)

    // Shared Pref For Dark Mode
    val darkMode by dataStoreManager.darkMode.collectAsState(false)

    // Shared Pref For Digital Jaap
    val title by dataStoreManager.title.collectAsState("Digital Jaap")

    // Shared Pref For Beep Tone Sound
    val beepSoundEnabled by dataStoreManager.beepSoundEnabled.collectAsState(false)

    // Shared Pref For Vibration
    val vibrationEnabled by dataStoreManager.vibrationEnabled.collectAsState(true)

    // For Scroll View
    val scrollState = rememberScrollState()


    var isButtonEnabled by remember { mutableStateOf(true) } // Control for button enable/disable

    // LaunchedEffect to handle 1-second delay
    LaunchedEffect(isButtonEnabled) {
        if (!isButtonEnabled) {
            delay(100)
            isButtonEnabled = true // Re-enable button
        }
    }

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    if (countingDetails != null) {
        textState.value = TextFieldValue(countingDetails.countTitle)


    } else {
        textState.value = TextFieldValue("")
    }

    FullScreenBackground(prefs) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (darkMode) Color.White else Color(0XFF87490c),
                text = title.lowercase().replaceFirstChar { it.uppercaseChar() },
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
                        scope.launch { dataStoreManager.setBeepSoundEnabled(!beepSoundEnabled) }
                    })

                customButtons(vibrationBackgroundColor,
                    Res.drawable.vibrate,
                    vibrationEnabled,
                    darkMode = darkMode,
                    onClick = {
                        scope.launch { dataStoreManager.setVibrationEnabled(!vibrationEnabled) }
                    })

                customButtons(themBackgroundColor,
                    Res.drawable.palette,
                    false,
                    darkMode = darkMode,
                    onClick = {
                        showToast(
                            "Theme is on it's way, Will be launched in next version!!",
                            context
                        )
                    })

                customButtons(darkModBackgroundColor,
                    Res.drawable.moon_stars,
                    darkMode,
                    darkMode = darkMode,
                    onClick = {
                        scope.launch { dataStoreManager.setDarkMode(!darkMode) }
                    })
            }

            Row(modifier = Modifier.padding(top = 20.dp)
                .wrapContentWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if(!darkMode) Orange else Color.DarkGray),
                verticalAlignment = Alignment.CenterVertically)
            {
                Image( modifier = Modifier.size(28.dp).padding(start = 10.dp),
                    painter = painterResource(Res.drawable.ic_target),
                    contentDescription = "target",
                    colorFilter = ColorFilter.tint(Color.Red))

                Text(modifier = Modifier.padding(start = 10.dp),
                    color =  Color.White,
                    text = "$target")

                Image( modifier = Modifier.size(28.dp).padding( start = 10.dp),
                    painter = painterResource(Res.drawable.ic_mala),
                    contentDescription = "mala",
                    colorFilter = ColorFilter.tint(if (darkMode)Color.White else Color.Black))

                Text(modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.White ,
                    text = "$mala")

            }

            Box(
                modifier = Modifier.wrapContentSize().padding(top = 30.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Image(
                            painter = painterResource(if (darkMode) Res.drawable.ic_device_dark else Res.drawable.device_1),
                            contentDescription = "device",
                            modifier = Modifier.size(350.dp),
                            contentScale = ContentScale.Fit

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

                        Button( enabled = isButtonEnabled,
                            onClick = {
                                isButtonEnabled = false
                                if (vibrationEnabled) triggerVibration(context, 100)
                                if (beepSoundEnabled) playBeep(context)
                                if (counter < 9999) { scope.launch { dataStoreManager.setCounter(counter + 1) }
                                    if (counter == target){
                                        scope.launch { dataStoreManager.setMala(mala + 1)
                                        dataStoreManager.setCounter(0)}
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                disabledBackgroundColor = Color.Transparent,
                                disabledContentColor = Color.Transparent,
                                contentColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(top = 170.dp)
                                .size(width = 120.dp, height = 120.dp)
                                .clip(RoundedCornerShape(130.dp)),
                        ) {}

                        // Counter Reset Button
                        Button(
                            onClick = {
                                if (counter != 0 || target != 108 || mala != 0 ) {
                                    showResetBottomSheet = true
                                    showSaveBottomSheet = false
                                } else {
                                    showToast("Your jaap counter is already empty", context)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(top = 148.dp, start = 150.dp)
                                .wrapContentSize(),
                        ) {}
                    }

                    Button(
                        onClick = {
                            showSaveBottomSheet = true

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (darkMode) Color.Black else Orange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(top = 28.dp).height(60.dp)
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
            Spacer(modifier = Modifier.height(90.dp))
        }
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
            SaveBottomSheet(
                prefs= prefs,
                totalCount = target * mala + counter,
                darkMode = darkMode,
                countingDetails = countingDetails,
                countingDao = countingDao,
                id = id,
                onDismiss = {
                    hideKeyboard(context)
                    showSaveBottomSheet = false
                },
                onSave = {
                    hideKeyboard(context)
                    scope.launch {
                        dataStoreManager.setCounter(0)
                        showToast("Your jaap count has been saved successfully", context)
                    }
                    onDiscontinue()
                    showSaveBottomSheet = false
                    dataStoreManager.setBeepSoundEnabled(!beepSoundEnabled)
                    dataStoreManager.setBeepSoundEnabled(beepSoundEnabled)
                },
                showBottomSheet = showSaveBottomSheet,

                onFail = {
                    hideKeyboard(context)
                    showToast("Please enter a name to save your counter ", context)
                },
                noCount = {
                    showToast(
                        "Your jaap counter is empty. Start counting first !",
                        context
                    )
                })

        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
            resetBottomSheet(
                onDismiss = { showResetBottomSheet = false },
                darkMode = darkMode,
                onReset = {
                    CoroutineScope(Dispatchers.Main).launch {
                        dataStoreManager.setCounter(0)
                    }
                    showResetBottomSheet = false
                    onDiscontinue()
                    dataStoreManager.setBeepSoundEnabled(!beepSoundEnabled)
                    dataStoreManager.setBeepSoundEnabled(beepSoundEnabled)
                },
                showBottomSheet = showResetBottomSheet
            )

        }
    }
}

