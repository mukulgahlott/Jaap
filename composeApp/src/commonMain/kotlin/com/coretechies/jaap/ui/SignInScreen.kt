package com.coretechies.jaap.ui

import AppScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackCompose.ui.theme.Orange
import japp.composeapp.generated.resources.DigitalJaap
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.ic_login_left
import japp.composeapp.generated.resources.ic_login_right
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun RegistrationScreen( navController: NavController) {
    var userId by remember { mutableStateOf("") }
    var passCode by remember { mutableStateOf("") }
    var retypePassCode by remember { mutableStateOf("") }

    var isEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // App Title
        Image(
            painter = painterResource(Res.drawable.DigitalJaap),
            modifier = Modifier.padding(bottom = 8.dp, top = 10.dp).fillMaxWidth(0.5f),
            contentDescription = ""
        )

        // Register Text with Decorative Elements
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier.fillMaxWidth(0.3f),
                painter = painterResource(Res.drawable.ic_login_left),
                contentDescription = "",
            )

            Text(
                text = "Register",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0XFFE28B2A)
            )

            Image(
                modifier = Modifier.fillMaxWidth(0.55f),
                painter = painterResource(Res.drawable.ic_login_right),
                contentDescription = "",

                )
        }

        // Feature Description
        Text(
            text = "Access advance features like Guided Meditations, Daily Prayers, " +
                    "Customizable Reminders, Calming Sounds, & Community Support",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF666666)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // User ID Input
        InputField(
            label = "User ID",
            value = userId,
            onValueChange = { userId = it },
            placeholder = "Enter your user id"
        )

        // Pass Code Input
        InputField(
            label = "Pass Code",
            value = passCode,
            onValueChange = { if (it.length <= 4) passCode = it },
            placeholder = "Enter your 4 digit numeric passcode",
            keyboardType = KeyboardType.NumberPassword
        )

        // Re-type Pass Code Input
        InputField(
            label = "Re-type Pass Code",
            value = retypePassCode,
            onValueChange = { if (it.length <= 4) retypePassCode = it },
            placeholder = "Enter your new 4 digit numeric passcode",
            keyboardType = KeyboardType.NumberPassword
        )

        // Create Account Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 5.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Orange,
                contentColor = Color.White,
                disabledBackgroundColor = Orange,
                disabledContentColor = Orange
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevation(8.dp)
        ) {
            Text(
                text = "Create Account",
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(.97f)
                .background(
                    color = Color(0XFFfaf6f3),
                    shape = RoundedCornerShape(12.dp)
                )
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(enabled = isEnabled) {
                    navController.popBackStack()
                    isEnabled = false
                    coroutineScope.launch {
                        delay(1000) // 1 second delay
                        isEnabled = true
                    }
                }
        )
        {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Login",
                    color = Orange,
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF663300)
            )
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0XFF87490C)) },
            modifier = Modifier
                .fillMaxWidth()

                .background(Color.White, RoundedCornerShape(3.dp)),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                focusedBorderColor = Orange,
                unfocusedBorderColor = Color(0XFF87490C),
                placeholderColor = Color.Gray,
                unfocusedLabelColor = Color(0XFF87490C),
                focusedLabelColor = Orange
            ),
            singleLine = true,
        )

    }
}