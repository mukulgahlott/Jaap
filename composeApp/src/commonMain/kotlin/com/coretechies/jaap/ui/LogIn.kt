package com.coretechies.jaap.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackCompose.ui.theme.Orange

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // App Logo and Title
        AppLogo()

        // Login Form
        LoginForm(
            userId = userId,
            passCode = passCode,
            isError = isError,
            onUserIdChange = { userId = it },
            onPassCodeChange = { if (it.length <= 4) passCode = it },
            onForgotPasswordClick = onForgotPasswordClick,
            onLoginClick = {
                isError = userId.isEmpty() || passCode.length != 4
//                if (!isError) onLoginClick(userId, passCode)
            }
        )

        // Additional Options
        LoginOptions(
            onContinueWithoutLogin = onContinueWithoutLogin,
            onRegisterClick = onRegisterClick
        )
    }
}

@Composable
private fun AppLogo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    shape = RoundedCornerShape(50.dp),
                    color = Color.DarkGray
                )
        )

        Text(
            text = "Digital Jaap",
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium
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
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Login",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

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
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End),
                fontSize = 14.sp
            )
        }

        // Login Button
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(
                text = "Login",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun LoginOptions(
    onContinueWithoutLogin: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Continue without login",
            fontSize = 16.sp,
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",

                fontSize = 16.sp
            )
            Text(
                text = "Register",

                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
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
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Orange
            ),
            singleLine = true,
            isError = isError
        )
    }
}