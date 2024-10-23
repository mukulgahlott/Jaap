package com.coretechies.jaap.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coretechies.jaap.cardview.CardViewJaap
import customButtons
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.list
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.volume
import org.jetbrains.compose.resources.stringResource

data class JaapData(val count: String, val title: String, val time: String)

@Composable
fun ListScreen() {
    val moonBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val volumeBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }

    // Example data
    val jaapItems = remember {
        mutableStateListOf(
            JaapData("800", "Gayatri-Mantra", "Wed. 17-Oct-2024 . 01:03 pm"),
            JaapData("500", "Maha Mrityunjaya", "Thu. 18-Oct-2024 . 10:00 am"),
            JaapData("1000", "Hanuman Chalisa", "Fri. 19-Oct-2024 . 06:00 am")
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fixed Header
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            customButtons(volumeBackgroundColor, Res.drawable.volume)

            Text(
                modifier = Modifier.wrapContentHeight(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF87490c),
                text = stringResource(Res.string.list),
                textAlign = TextAlign.Center
            )

            customButtons(moonBackgroundColor, Res.drawable.moon_stars)
        }

        // LazyColumn to display the list of Jaap cards
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(jaapItems) { item ->
                CardViewJaap(item)
            }
        }
    }
}

