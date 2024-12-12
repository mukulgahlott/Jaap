package com.coretechies.jaap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import japp.composeapp.generated.resources.AReligiousLeaderboard
import japp.composeapp.generated.resources.AReligiousLeaderboardDescription
import japp.composeapp.generated.resources.GoSocialWithAnusthan
import japp.composeapp.generated.resources.GoSocialWithAnusthanDescription
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bc_splash
import japp.composeapp.generated.resources.bg_walkthrough
import japp.composeapp.generated.resources.ic_BackButton
import japp.composeapp.generated.resources.ic_arrow
import japp.composeapp.generated.resources.ic_walkthrough_1
import japp.composeapp.generated.resources.ic_walkthrough_2
import japp.composeapp.generated.resources.ic_walkthrough_3
import japp.composeapp.generated.resources.letsStart
import japp.composeapp.generated.resources.theDigitalDivine
import japp.composeapp.generated.resources.theDigitalDivineDescription
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WalkThrough(onNext: () -> Unit, navController: NavController) {
    // State for the current index
    var currentIndex by remember { mutableStateOf(0) }

    // Define your walkthrough content
    val walkthroughContents = listOf(
        Triple(Res.drawable.ic_walkthrough_1, stringResource(Res.string.theDigitalDivine), stringResource(Res.string.theDigitalDivineDescription)),
        Triple(Res.drawable.ic_walkthrough_2, stringResource(Res.string.GoSocialWithAnusthan), stringResource(Res.string.GoSocialWithAnusthanDescription)),
        Triple(Res.drawable.ic_walkthrough_3, stringResource(Res.string.AReligiousLeaderboard), stringResource(Res.string.AReligiousLeaderboardDescription))
    )

    // Ensure index is within bounds
    val isFirst = currentIndex == 0
    val isLast = currentIndex == walkthroughContents.lastIndex

    val (image, title, description) = walkthroughContents[currentIndex]

    // Animation state
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(currentIndex) {
        isVisible = false // Hide the previous content
        delay(200) // Add a short delay for transition
        isVisible = true // Show the new content
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.bg_walkthrough),
            contentDescription = "Static Background Image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Walkthrough Content with Animation
            contentWalkthrough(
                mainImage = image,
                title = title,
                description = description,
                isVisible = isVisible
            )

            // Pagination Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = if (currentIndex == index) Color(0xFFCC6600)
                                else Color(0xFFCCCCCC),
                                shape = CircleShape
                            )
                    )
                }
            }

            // Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.clip(CircleShape)
                ) {
                    if (!isFirst)
                    Image(
                        modifier = Modifier.clip(CircleShape).size(60.dp).clickable {  if (!isFirst) currentIndex-- },
                        painter = painterResource(Res.drawable.ic_BackButton),
                        contentDescription = "" ,
                    )
                }

                // Next Button
                Button(
                    onClick = {
                        if (currentIndex < 2) currentIndex++ else {
                            onNext()
                            navController.navigate(AppScreen.LogIn.route)
                        }
                    },
                    modifier = Modifier.height(60.dp).width(if (isLast) 140.dp else 60.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFCC6600))
                ) {
                    if (!isLast) {
                        Image(
                            painter = painterResource(Res.drawable.ic_arrow),
                            contentDescription = "Next",
                            modifier = Modifier.size(20.dp)
                        )
                    } else Text(text = stringResource(Res.string.letsStart) , color = Color.White, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
