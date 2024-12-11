package com.coretechies.jaap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bc_splash
import org.jetbrains.compose.resources.painterResource


@Composable
fun WalkThrough(onTimeout: () -> Unit) {
    Column (

        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.fillMaxWidth(0.2f))
        Image(
            modifier = Modifier.fillMaxWidth().padding(30.dp).fillMaxHeight(0.3f),
            painter = painterResource(Res.drawable.bc_splash),
            contentDescription = "static splash Screen",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "",
            color = Color(0XFF87490C),
        )

    }
}