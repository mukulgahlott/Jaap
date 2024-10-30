import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.no
import japp.composeapp.generated.resources.reset_counter
import japp.composeapp.generated.resources.reset_description
import japp.composeapp.generated.resources.yes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

@Composable
fun SaveBottomSheet(
    onDismiss: () -> Unit,
    countingDao: CountingDao,
    countingDetails: CountingDetails?,
    darkMode: Boolean,
    onSave: () -> Unit,
    onFail: () -> Unit,
    totalCount: Int,
    showBottomSheet: Boolean
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    if (countingDetails != null) {
        textState.value = TextFieldValue(countingDetails.countTitle)
    }else{
        textState.value = TextFieldValue("")
    }

    AnimatedVisibility(
        visible = showBottomSheet, enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        ), exit = slideOutVertically(
            targetOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = if (darkMode) Color.Black  else Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Header with close and check buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp).clickable {
                            onDismiss()
                        }) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFb7926d),
                            modifier = Modifier.size(40.dp)
                        ) {}
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }

                    Text(
                        text = "Save", style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8C4B26)
                        )
                    )

                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp).clickable {
                            onDismiss()
                        }) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFe28b2a),
                            modifier = Modifier.size(40.dp)
                        ) {}
                        IconButton(onClick = {

                            if (countingDetails != null) {
                                updateCounter(
                                    totalCount = totalCount,
                                    countTitle = textState.value.text,
                                    countingDao = countingDao,
                                    id = countingDetails.id,
                                    onFail = {
                                        onFail()
                                    }
                                )
                            }
                            else {
                                insertList(
                                    totalCount,
                                    textState.value.text,
                                    "Jajman0900",
                                    "Jajman-0900",
                                    countingDao, onFail={
                                        onFail()
                                    }
                                )
                            }
                            onSave()
                            textState.value = TextFieldValue("")


                        }) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Save",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Text Field for Name input

                TextField(
                    value = textState.value,
                    onValueChange = { if (it.text.length <= 16) textState.value = it },
                    placeholder = { Text(text = "Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF1EAE2), // Light background color
                        placeholderColor = Color(0xFF9D8E80),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Total Count text
                Text(
                    text = "Total Count : $totalCount", style = TextStyle(
                        color = Color(0xFFFF8C00), // Custom color for count text
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}


fun insertList(
    totalCount: Int,
    countTitle: String,
    countingDetailsUserId: String,
    countingDetailsUserNane: String,
    countingDao: CountingDao,
    onFail: () -> Unit
) {

    if (countTitle.isNotBlank()) {
        val countingTempObj = CountingDetails(
            totalCount = totalCount,
            countTitle = countTitle,
            countDate = getCurrentDateTime(),
            countingDetailsUserId = countingDetailsUserId,
            countingDetailsUserName = countingDetailsUserNane
        )
        CoroutineScope(Dispatchers.IO).launch {
            countingDao.insert(countingTempObj)
        }

    }
    else{
        onFail()
    }
}

fun updateCounter(
    countingDao: CountingDao, totalCount: Int,
    countTitle: String, id: Int, onFail: () -> Unit
) {
    if (countTitle.isNotBlank()) {
        CoroutineScope(Dispatchers.IO).launch {
            countingDao.updateById(id, totalCount, countTitle, getCurrentDateTime())
        }
    }
    else{
        onFail()
    }
}


fun getCurrentDateTime(): String {
    val currentMoment = Clock.System.now()
    val dateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

    // Format date as "DD-MM-YYYY"
    val date = "${dateTime.dayOfMonth}-${dateTime.month.name.take(3).lowercase().replaceFirstChar { it.uppercaseChar() } }-${dateTime.year}"

    val hour = dateTime.hour % 12
    val amPm = if (dateTime.hour >= 12) "pm" else "am"
    val minute = dateTime.minute.toString().padStart(2, '0')

    val time = "${if (hour == 0) 12 else hour}:$minute $amPm"

    val weekOfTheDay = dateTime.dayOfWeek.name
    val firstThreeChars = weekOfTheDay.substring(0, 3).lowercase().replaceFirstChar { it.uppercaseChar() }

    return "${firstThreeChars} . $date . $time"
}


@Composable
fun resetBottomSheet(
    onDismiss: () -> Unit, onReset: () -> Unit, showBottomSheet: Boolean, darkMode : Boolean
) {


    AnimatedVisibility(
        visible = showBottomSheet, enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        ), exit = slideOutVertically(
            targetOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = if (darkMode) Color.Black  else Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = stringResource(Res.string.reset_counter),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (darkMode) Color.White else Color(0xFF8C4B26)
                        )
                    )
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp).clickable {
                            onDismiss()
                        }) {
                        Surface(
                            shape = CircleShape,
                            color = if (darkMode) Color(0XFF361d05) else Color(0xFFcfb69e),
                            modifier = Modifier.size(40.dp)
                        ) {}
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text(
                        text = stringResource(Res.string.reset_description), style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (darkMode) Color.White else Color(0xFF8C4B26)
                        )
                    )

                }

                Button(
                    onClick = {
                        onReset()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0XFFf36464), contentColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 10.dp).align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.9f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.yes),
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (darkMode) Color.Black else Color.White,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 10.dp).align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.9f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.no),
                        color = if (darkMode) Color.White else Color(0xFF87490c),
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }


            }
        }

    }
}



