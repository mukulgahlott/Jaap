import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackCompose.ui.theme.Orange
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.device_1
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.palette
import japp.composeapp.generated.resources.save
import japp.composeapp.generated.resources.vibrate
import japp.composeapp.generated.resources.volume
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen() {

    val volumeBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val vibrationBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val theamBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val darkModBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }


    var showSaveBottomSheet by remember { mutableStateOf(false) }
    var showResetBottomSheet by remember { mutableStateOf(false) }
    // counter state
    var defaultCounterCount by remember { mutableStateOf(0) }




    FullScreenBackground {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF87490c),
                text = "Digital Mala Jaap",
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                customButtons(volumeBackgroundColor, Res.drawable.volume)
                customButtons(vibrationBackgroundColor, Res.drawable.vibrate)
                customButtons(theamBackgroundColor, Res.drawable.palette)
                customButtons(darkModBackgroundColor, Res.drawable.moon_stars)
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
                            painter = painterResource(Res.drawable.device_1),
                            contentDescription = "device",
                            modifier = Modifier.size(350.dp)

                        )

                        Text(
                            text = if (defaultCounterCount == 0) "0000" else "${defaultCounterCount}",
                            modifier = Modifier.padding(top = 77.dp),
                            fontSize = 50.sp,
                            textAlign = TextAlign.End,
                        )

                        Button(
                            onClick = {
                                triggerVibration(context, 100)
                                defaultCounterCount++
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
                            backgroundColor = Orange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .fillMaxWidth(fraction = 0.7f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.save),
                            modifier = Modifier.padding(8.dp),
                            fontSize = 26.sp,
                        )
                    }
                }
            }


        }
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                SaveBottomSheet(
                    totalCount = defaultCounterCount,
                    onDismiss = { showSaveBottomSheet = false },
                    onSave = { showSaveBottomSheet = false },
                    showBottomSheet = showSaveBottomSheet
                )

            }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
            resetBottomSheet(
                onDismiss = { showResetBottomSheet = false },
               onReset = { showResetBottomSheet = false
                   defaultCounterCount = 0 },
                showBottomSheet = showResetBottomSheet)

        }

        }
}

