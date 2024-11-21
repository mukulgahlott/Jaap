import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.coretechies.jaap.dataStore.DataStoreManager
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import japp.composeapp.generated.resources.*
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
    prefs: DataStore<Preferences>,
    countingDao: CountingDao,
    id: Long,
    countingDetails: CountingDetails?,
    darkMode: Boolean,
    onSave: () -> Unit,
    onFail: () -> Unit,
    noCount: () -> Unit,
    totalCount: Int,
    showBottomSheet: Boolean,
) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val target = remember { mutableStateOf(TextFieldValue("")) }
    val performUpdate = remember { mutableStateOf(true) }
    var previousName = ""

    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)
    val title by dataStoreManager.title.collectAsState("Digital Jaap")


    target.value = TextFieldValue("")
    textState.value = TextFieldValue(title)
    previousName = title
    if (countingDetails != null) {
        performUpdate.value = countingDetails.totalCount != totalCount
    }

    AnimatedVisibility(
        visible = showBottomSheet, enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        ), exit = slideOutVertically(
            targetOffsetY = { it }, animationSpec = tween(durationMillis = 700)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = if (darkMode) Color.Black else Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Header with close and check buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)
                    ) {}
                    Text(
                        text = stringResource(Res.string.save), style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8C4B26)
                        )
                    )

                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFe28b2a),
                            modifier = Modifier.size(40.dp)
                        ) {}
                        IconButton(onClick = {
                            if (id.toInt() != 0 || countingDetails != null) {
                                updateCounter(
                                    totalCount = totalCount,
                                    countTitle = textState.value.text,
                                    countingDao = countingDao,
                                    onFail = {
                                        onFail()
                                    },
                                    onSave = onSave,
                                    target = if (target.value.text.isNotBlank() && target.value.text.toInt() != 0) target.value.text.toInt() else 108,
                                    id = id
                                )
                            } else {
                                insertList(totalCount,
                                    textState.value.text,
                                    countingDao,
                                    onFail = {},
                                    onSave = {
                                        onSave()
                                        scope.launch {
                                            dataStoreManager.setTarget(if (target.value.text.isNotBlank() && target.value.text.toInt() != 0) target.value.text else "108")
                                            dataStoreManager.setTitle(textState.value.text)
                                        }
                                    },
                                    noCount = noCount,
                                    target = if (target.value.text.isNotBlank() && target.value.text.toInt() != 0) target.value.text.toInt() else 108,
                                    scope = scope,
                                    dataStoreManager = dataStoreManager
                                )

                            }

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

                TextField(value = textState.value,
                    onValueChange = {
                        textState.value = it
                        if (countingDetails != null) {
                            if (previousName != it.text) {
                                performUpdate.value = true
                            } else {
                                countingDetails.countTitle = it.text
                            }
                        }
                    },
                    placeholder = { Text(text = stringResource(Res.string.name)) },
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

                TextField(value = target.value,
                    onValueChange = { newText ->
                        // Create a new TextFieldValue that preserves the cursor position
                        if (newText.text.length <= 4) target.value = TextFieldValue(
                            text = newText.text.filter { it != '.' && it != ',' && it != ' ' && it != '-' },
                            selection = TextRange(newText.text.length) // Set cursor at the end
                        )
                    },
                    placeholder = { Text(stringResource(Res.string.Set_target_default)) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF1EAE2), // Light background color
                        placeholderColor = Color(0xFF9D8E80),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Total Count text
                Text(
                    text = stringResource(Res.string.totalCount)+" : $totalCount", style = TextStyle(
                        color = Color(0xFFFF8C00), // Custom color for count text
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}


fun insertList(
    totalCount: Int,
    countTitle: String,
    countingDao: CountingDao,
    onFail: () -> Unit,
    noCount: () -> Unit,
    onSave: () -> Unit,
    target: Int,
    scope: CoroutineScope,
    dataStoreManager: DataStoreManager
) {

    if (countTitle.isNotBlank()) {

        val countingTempObj = CountingDetails(
            totalCount = totalCount,
            countTitle = countTitle,
            countDate = getCurrentDateTime(),
            countingDetailsUserId = "Jajman0900",
            countingDetailsUserName = "Jajman-0900",
            target = target
        )
        CoroutineScope(Dispatchers.IO).launch {
            onSave()
            val id = countingDao.insert(countingTempObj)
            scope.launch {
                dataStoreManager.setId(id)
            }
        }
    } else {
        onFail()
    }
}

fun updateCounter(
    countingDao: CountingDao,
    totalCount: Int,
    id: Long,
    countTitle: String,
    onFail: () -> Unit,
    onSave: () -> Unit,
    target: Int
) {


    if (countTitle.isNotBlank()) {
        CoroutineScope(Dispatchers.IO).launch {

            val countingDetailsTemp = CountingDetails(
                id = id,
                totalCount = totalCount,
                countTitle = countTitle,
                countDate = getCurrentDateTime(),
                countingDetailsUserId = "Jajman0900",
                countingDetailsUserName = "Jajman0900",
                target = target
            )
            countingDao.updateById(countingDetailsTemp)
            onSave()
        }
    } else {
        onFail()
    }
}


fun getCurrentDateTime(): String {
    val currentMoment = Clock.System.now()
    val dateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

    // Format date as "DD-MM-YYYY"
    val date = "${dateTime.dayOfMonth}-${
        dateTime.month.name.take(3).lowercase().replaceFirstChar { it.uppercaseChar() }
    }-${dateTime.year}"

    val hour = dateTime.hour % 12
    val amPm = if (dateTime.hour >= 12) "pm" else "am"
    val minute = dateTime.minute.toString().padStart(2, '0')

    val time = "${if (hour == 0) 12 else hour}:$minute $amPm"

    val weekOfTheDay = dateTime.dayOfWeek.name
    val firstThreeChars =
        weekOfTheDay.substring(0, 3).lowercase().replaceFirstChar { it.uppercaseChar() }

    return "${firstThreeChars} . $date . $time"
}


@Composable
fun resetBottomSheet(
    onDismiss: () -> Unit, onReset: () -> Unit, showBottomSheet: Boolean, darkMode: Boolean
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
            color = if (darkMode) Color.Black else Color.White
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
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)
                    ) {
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
                    modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.95f),
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
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.no),
                        color = if (darkMode) Color.White else Color(0xFF87490c),
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(80.dp))


            }
        }

    }
}


@Composable
fun discontinueBottomSheet(
    onDismiss: () -> Unit, onReset: () -> Unit, showBottomSheet: Boolean, darkMode: Boolean
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
            color = if (darkMode) Color.Black else Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = stringResource(Res.string.startNewJaap),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (darkMode) Color.White else Color(0xFF8C4B26)
                        )
                    )
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)
                    ) {
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
                        text = stringResource(Res.string.startNewJaap_text),
                        style = TextStyle(
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
                    modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.95f),
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
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.no),
                        color = if (darkMode) Color.White else Color(0xFF87490c),
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }

    }
}