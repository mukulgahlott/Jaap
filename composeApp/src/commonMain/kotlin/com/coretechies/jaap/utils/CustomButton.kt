import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.jetpackCompose.ui.theme.PureOrange
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.ic_right_arrow
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun customButtons(
    backgroundColor: MutableState<Color>,
    icon: DrawableResource,
    enabled: Boolean,
    onClick: () -> Unit,
    darkMode: Boolean
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp).clip(RoundedCornerShape(60.dp))
    ) {
        Surface(shape = CircleShape,
            color = if (enabled) PureOrange else if (darkMode) Color(0XFF4a4a4a) else Color(
                0xFFb7926d
            ),
            modifier = Modifier.size(50.dp).clickable {
                    backgroundColor.value = if (enabled) {
                        PureOrange
                    } else {
                        Color(0xFFb7926d)
                    }
                    onClick()
                }) {}
            Image(
                painter = painterResource(icon),
                colorFilter = ColorFilter.tint(if (!darkMode) Color.White else Color.Black),
                contentDescription = "Circular Image",
                modifier = Modifier.size(30.dp)
            )
    }
}
@Composable
fun RenderCustomButton(
    showSwitch: Boolean,
    switchState :Boolean,
    icon: Painter,
    title: String,
    description: String,
    topMargin: Dp,
    showDescription: Boolean,
    modifier: Modifier = Modifier,
    darkMode: Boolean,
    onClick: () -> Unit,
    onSwitch: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(true) }
    isChecked.value = switchState
    var isButtonEnabled by remember { mutableStateOf(true) } // Control for button enable/disable

    // LaunchedEffect to handle 1-second delay
    LaunchedEffect(isButtonEnabled) {
        if (!isButtonEnabled) {
            delay(1000) // Wait for 1 second
            isButtonEnabled = true // Re-enable button
        }
    }

    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = topMargin)
                .clip(RoundedCornerShape(10.dp))
                .background(if (darkMode) Color(0XFF2c2c2c) else Color(0XFFf3ede7)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier.clickable(enabled = isButtonEnabled) { // Use isButtonEnabled here
                    isButtonEnabled = false // Disable button
                    onClick()
                }.fillMaxWidth().padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = if (showDescription) 14.dp else 22.dp,
                    bottom = if (showDescription) 14.dp else 22.dp
                ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = icon,
                    contentDescription = "Main Icon",
                    colorFilter = ColorFilter.tint(if (darkMode) Color.White else Color(0XFF87490c)),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Fit,
                )

                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (darkMode) Color.White else Color(0XFF87490c),
                        modifier = Modifier.wrapContentSize().padding(start = 24.dp)
                    )
                    if (showDescription) {
                        Text(
                            text = description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (darkMode) Color.Gray else Color(0XFF87490c),
                            modifier = Modifier.wrapContentSize().padding(start = 24.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                ) {
                    if (showSwitch) {
                        Switch(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it
                                              onSwitch(isChecked.value) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0XFFe28b2a)
                            )
                        )
                    } else {
                        Image(
                            painter = painterResource(Res.drawable.ic_right_arrow),
                            contentDescription = "Right Arrow",
                            modifier = Modifier.size(width = 30.dp, height = 30.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}
