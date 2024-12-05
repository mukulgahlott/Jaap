package com.coretechies.jaap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bc_splash
import org.jetbrains.compose.resources.painterResource


@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }
    Box(

        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        LottieAnimation(composition = composition)

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.bc_splash),
            contentDescription = "static splash Screen",
            contentScale = ContentScale.FillBounds

        )
    }
}