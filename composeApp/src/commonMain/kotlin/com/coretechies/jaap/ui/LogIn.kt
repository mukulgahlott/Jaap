package com.coretechies.jaap.ui

import AppScreen
import LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coretechies.jaap.api.ApiClient
import com.coretechies.jaap.api.ApiService
import com.coretechies.jaap.utils.getDeviceId
import com.coretechies.jaap.utils.getFcmToken
import com.example.jetpackCompose.ui.theme.DarkOrange
import com.example.jetpackCompose.ui.theme.Orange
import com.example.jetpackCompose.ui.theme.OrangeSubColor
import com.example.jetpackCompose.ui.theme.PureOrange
import com.example.jetpackCompose.ui.theme.SubDark
import io.ktor.client.HttpClient
import japp.composeapp.generated.resources.DigitalJaap
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bg_logIn
import japp.composeapp.generated.resources.ic_login_left
import japp.composeapp.generated.resources.ic_login_right
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
//    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onContinueWithoutLogin: () -> Unit,
    onRegisterClick: () -> Unit,
    navController: NavController
) {
    var userId by remember { mutableStateOf("") }
    var passCode by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val apiService = ApiService(client = HttpClient()) // Initialize the HttpClient
    val loginViewModel = LoginViewModel(ApiService(ApiClient.client));
    val coroutineScope = rememberCoroutineScope()  // Remember CoroutineScope for UI-related tasks

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(Res.drawable.bg_logIn),
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // App Logo and Title
            AppLogo()

            // Login Form
            LoginForm(userId = userId,
                passCode = passCode,
                isError = isError,
                onUserIdChange = { userId = it },
                onPassCodeChange = { if (it.length <= 4) passCode = it },
                onLoginClick = {
                    // Triggering the login process when login button is clicked
                    isError = userId.isEmpty() || passCode.length != 4

                    // Ensure there is no error before proceeding
                    if (!isError) {
                        coroutineScope.launch {
                            // Perform user verification
                            val result = loginViewModel.verifyUser(
                                userName = userId,
                                passcode = passCode.toInt(),
                                deviceId = getDeviceId(),
                                fcmToken = getFcmToken()
                            )

                            // Check result (if needed, depending on API response)
                            if (result == "verified") {
                                // Navigate to the next screen if the user is verified
                                navController.navigate(AppScreen.CongratulationScreen.route)
                            } else {
                                // Handle error (e.g., show a message)
                                isError = true
                            }
                        }
                    }
                }
            )
            // Additional Options
            LoginOptions(
                onContinueWithoutLogin = onContinueWithoutLogin,
                onRegisterClick = { navController.navigate(AppScreen.SignIn.route) }
            )
        }
    }
}

@Composable
private fun AppLogo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.size(70.dp).background(
                color = Color.Transparent
            )
        )

        Image(modifier = Modifier.fillMaxWidth(0.7f),
            painter = painterResource(Res.drawable.DigitalJaap),
            contentDescription = "",

        )
    }
}

@Composable
private fun LoginForm(
    userId: String,
    passCode: String,
    isError: Boolean,
    onUserIdChange: (String) -> Unit,
    onPassCodeChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top=40.dp), verticalArrangement = Arrangement.spacedBy(19.dp)
    ) {

        Row (modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {

            Image(modifier = Modifier.fillMaxWidth(0.3f),
                painter = painterResource(Res.drawable.ic_login_left),
                contentDescription = "",
                )

            Text(
                text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color(0XFFE28B2A)
            )

            Image(modifier = Modifier.fillMaxWidth(0.55f),
                painter = painterResource(Res.drawable.ic_login_right),
                contentDescription = "",

                )
        }

        // User ID Input
        CustomTextField(
            value = userId,
            onValueChange = onUserIdChange,
            label = "User ID",
            placeholder = "Enter your user id",
            isError = isError && userId.isEmpty()
        )

        // Pass Code Input
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CustomTextField(
                value = passCode,
                onValueChange = onPassCodeChange,
                label = "Pass Code",
                placeholder = "Enter your 4 digit numeric passcode",
                isPassword = true,
                keyboardType = KeyboardType.NumberPassword,
                isError = isError && passCode.length != 4
            )

            Text(
                text = "",
                modifier = Modifier.align(Alignment.End).padding(vertical = 1.dp),
                fontSize = 14.sp
            )
        }

        // Login Button
        Button(enabled = (userId.isNotBlank() && passCode.length == 4),
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 5.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Orange, // Custom background color
                contentColor = Color.White,          // Text color
                disabledBackgroundColor = Orange, // Disabled state background color
                disabledContentColor = Orange // Disabled state text color
            ),
            shape = RoundedCornerShape(12.dp), // Optional: Rounded corners
            elevation = ButtonDefaults.elevation(8.dp) // Optional: Elevation for shadow
        ) {
            Text(
                text = "Login",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold // Optional: Make the text bold
            )
        }
    }
}

@Composable
private fun LoginOptions(
    onContinueWithoutLogin: () -> Unit, onRegisterClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Continue without login",
            fontSize = 16.sp,
            color = Orange
        )

        Box(
            modifier = Modifier.fillMaxWidth(.97f)
                .background(
                    color = Color(0XFFfaf6f3),
                    shape = RoundedCornerShape(12.dp) // Adjust corner radius as needed
                ).height(50.dp)
                .clip(RoundedCornerShape(12.dp)).clickable{ onRegisterClick() }
                .padding(horizontal = 15.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxSize(), // Ensure Row fills the Box
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 16.sp
                )
                Text(
                    text = "Register",
                    color = Orange,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 5.dp)) {
        Text(
            fontWeight = FontWeight.Medium,
            text = label, fontSize = 16.sp, color = Color(0XFF87490C),
        )
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = Color(0XFF87490C)) },
                modifier = Modifier
                    .fillMaxWidth()

                    .background(Color.White, RoundedCornerShape(3.dp)), // Background with rounded corners
                shape = RoundedCornerShape(10.dp), // Apply rounded corners to the TextField itself
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent, // Set background to transparent (handled by Modifier)
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Color(0XFF87490C),
                    placeholderColor = Color.Gray,
                    unfocusedLabelColor = Color(0XFF87490C),
                    focusedLabelColor = Orange
                ),
                singleLine = true,
                isError = isError
            )

    }
}