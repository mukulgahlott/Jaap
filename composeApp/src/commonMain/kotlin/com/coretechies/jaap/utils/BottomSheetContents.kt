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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackCompose.ui.theme.Orange
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.no
import japp.composeapp.generated.resources.reset_counter
import japp.composeapp.generated.resources.reset_description
import japp.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.stringResource

@Composable
fun SaveBottomSheet(
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    totalCount: Int,
    showBottomSheet: Boolean
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    AnimatedVisibility(
        visible = showBottomSheet,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Header with close and check buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp)
                            .clickable {
                                onDismiss()
                            }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFb7926d),
                            modifier = Modifier.size(40.dp)
                        ) {
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }

                    Text(
                        text = "Save",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8C4B26)
                        )
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp)
                            .clickable {
                                onDismiss()
                            }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFe28b2a),
                            modifier = Modifier.size(40.dp)
                        ) {
                        }
                        IconButton(onClick = onSave) {
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
                    onValueChange = { textState.value = it },
                    placeholder = { Text(text = "Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF1EAE2), // Light background color
                        placeholderColor = Color(0xFF9D8E80) // Placeholder color
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Total Count text
                Text(
                    text = "Total Count : $totalCount",
                    style = TextStyle(
                        color = Color(0xFFFF8C00), // Custom color for count text
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}


@Composable
fun resetBottomSheet(
    onDismiss: () -> Unit,
    onReset: () -> Unit,
    showBottomSheet: Boolean
) {


    AnimatedVisibility(
        visible = showBottomSheet,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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
                            color = Color(0xFF8C4B26)
                        )
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp)
                            .clickable {
                                onDismiss()
                            }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFcfb69e),
                            modifier = Modifier.size(40.dp)
                        ) {
                        }
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
                        text = stringResource(Res.string.reset_description),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8C4B26)
                        )
                    )

                }

                Button(
                    onClick = {
                        onReset()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Orange,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.7f),
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
                        backgroundColor = Orange,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp).align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.7f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.no),
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }


            }
        }

    }
}


