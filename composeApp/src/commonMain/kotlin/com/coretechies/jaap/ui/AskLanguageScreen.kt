package com.coretechies.jaap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.ic_tick
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChooseLanguageScreen(
    onNextClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLanguage by remember { mutableStateOf("English") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center // Centers the column vertically and horizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Language",
                fontSize = 21.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2B2B2B),
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LanguageButton(
                    text = "हिन्दी",
                    isSelected = selectedLanguage == "Hindi",
                    onClick = { selectedLanguage = "Hindi" }
                )


                LanguageButton(
                    text = "English",
                    isSelected = selectedLanguage == "English",
                    onClick = { selectedLanguage = "English" }
                )
            }

            Spacer(modifier = Modifier.height(80.dp)) // Adjust the spacing to align with screenshot

            Button(
                onClick = {onNextClick(selectedLanguage)
                          },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFE28B2A)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun LanguageButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(130.dp)
            .height(50.dp)
            .background(
                color = if (isSelected) Color(0xFFF9753C) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFF9753C) else Color(0xFF8E94A6),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = if (text == "हिन्दी") 24.sp else 22.sp,
                fontWeight = if (text == "English") FontWeight.Normal else FontWeight.Normal,
                color = if (isSelected) Color.White else Color(0xFF8E94A6)
            )

            if (isSelected) {
                Spacer(modifier = Modifier.width(8.dp))
                CheckmarkIcon()
            }
        }
    }
}

@Composable
private fun CheckmarkIcon() {
    Box(
        modifier = Modifier
            .size(23.dp)
            .background(
                color = Color(0xFF72CA3A),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Placeholder for checkmark vector
        Image(
            modifier = Modifier.padding(6.dp),
            painter = painterResource(Res.drawable.ic_tick),
            contentDescription = "tick",
        )
    }
}
