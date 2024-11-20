package com.coretechies.jaap.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.launch
import updateCounter

@Composable
fun targetEdit(
    onDismiss: () -> Unit,
    countingDao: CountingDao,
    id: Long,
    darkMode: Boolean,
    onSave: () -> Unit,
    totalCount: Int,
    showBottomSheet: Boolean,
    prefs: DataStore<Preferences>
) {
    val target = remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(prefs, scope)
    val title = dataStoreManager.title.collectAsState("Digital Jaap")

    target.value = TextFieldValue("")

    if (showBottomSheet) {
        // Parent container that covers the whole screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onDismiss() // Call the onDismiss callback when clicking outside
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center) // Center the child Box within the parent Box
                    .clickable(enabled = false) {} // Prevent clicks on the Surface from propagating
            ) {
                AnimatedVisibility(
                    visible = showBottomSheet,
                    enter = scaleIn(
                        initialScale = 0.5f, // Start smaller and grow to full size
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(
                        initialAlpha = 0.3f, // Start with partial opacity
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = scaleOut(
                        targetScale = 1.2f, // Grow slightly larger before disappearing
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(
                        targetAlpha = 0.3f, // Fade out gradually
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(bottom = 50.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = if (darkMode) Color.Black else Color.White
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            // Header with check button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.size(50.dp)) // Empty space to balance row layout

                                Text(
                                    text = "Edit Target",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF8C4B26)
                                    )
                                )

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFFe28b2a),
                                        modifier = Modifier.size(40.dp)
                                    ) {}
                                    IconButton(onClick = {
                                        try {
                                            val targetValue = if (target.value.text.isNotBlank() && target.value.text.toInt() != 0) target.value.text.toInt() else 108
                                            scope.launch {
                                                updateCounter(
                                                    totalCount = totalCount,
                                                    countTitle = title.value,
                                                    countingDao = countingDao,
                                                    onFail = {
                                                    },
                                                    onSave = onSave,
                                                    target = if (targetValue != 0) targetValue else 108,
                                                    id = id
                                                )
                                                dataStoreManager.setMala(totalCount/targetValue)
                                                dataStoreManager.setCounter(totalCount % targetValue)
                                                dataStoreManager.setTarget(targetValue.toString())
                                            }
                                        } catch (e: NumberFormatException) {
                                            // Handle exception for non-numeric input
                                        }
                                        onDismiss()
                                    }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Save",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Text Field for Name input
                            TextField(
                                value = target.value,
                                onValueChange = { newText ->
                                    if (newText.text.length <= 4) {
                                        target.value = TextFieldValue(
                                            text = newText.text.filter { it.isDigit() },
                                            selection = TextRange(newText.text.length) // Set cursor at the end
                                        )
                                    }
                                },
                                placeholder = { Text(text = "Set target (default is 108)") },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color(0xFFF1EAE2),
                                    placeholderColor = Color(0xFF9D8E80),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
