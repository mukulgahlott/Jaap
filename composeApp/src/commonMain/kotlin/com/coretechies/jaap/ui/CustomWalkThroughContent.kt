package com.coretechies.jaap.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bg_walkthrough
import japp.composeapp.generated.resources.ic_walkthrough_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun contentWalkthrough(
    mainImage: DrawableResource,
    title: String,
    description: String,
    isVisible: Boolean
) {
    // Define the animation specs
    val transition = remember { Animatable(-1000f) } // Start off-screen (left)

    LaunchedEffect(isVisible) {
        if (isVisible) {
            transition.animateTo(
                targetValue = 0f, // End at the original position
                animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .fillMaxHeight(0.7f)
            .offset(x = transition.value.dp), // Animate offset
        contentAlignment = Alignment.TopCenter
    ) {
        Column (modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .fillMaxHeight(if (mainImage== Res.drawable.ic_walkthrough_3)0.525f else 0.62f),
                painter = painterResource(mainImage),
                contentDescription = "",
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.fillMaxHeight(if (mainImage == Res.drawable.ic_walkthrough_3) 0.21f else 0.02f))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF87490C),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                textAlign = TextAlign.Center,
                color = Color(0XFF87490C) ,
                fontSize = 16.sp,
                text = description
            )
        }
    }
}
