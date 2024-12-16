package com.coretechies.jaap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackCompose.ui.theme.Orange
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.ic_congrathes
import org.jetbrains.compose.resources.painterResource

@Composable
fun CongratsScreen(
    onContinueClick: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Congrats Illustration Frame
        Box(
            modifier = Modifier.fillMaxWidth(0.9f),
            contentAlignment = Alignment.Center
        ) {

            // Congrats Text
            Image(
                painter = painterResource(Res.drawable.ic_congrathes),
                modifier = Modifier.padding().fillMaxWidth(1f),
                contentDescription = ""
            )
        }

        // Success Message
        Text(
            text = "Your account has been created successfully",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xDE000000),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 26.dp).padding(bottom = 20.dp)
        )

        // Continue Button
        Button(
            onClick = onContinueClick,
            modifier = Modifier.fillMaxWidth(0.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFFA56728)
                , backgroundColor = Orange
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "Continue",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                )
            )
        }
    }
}
