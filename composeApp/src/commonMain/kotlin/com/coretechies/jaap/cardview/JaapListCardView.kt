package com.coretechies.jaap.cardview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coretechies.jaap.room.counter.CountingDetails
import com.example.jetpackCompose.ui.theme.Orange
import com.example.jetpackCompose.ui.theme.PureOrange
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.contin
import japp.composeapp.generated.resources.delete
import japp.composeapp.generated.resources.ic_delete
import japp.composeapp.generated.resources.ic_mala
import japp.composeapp.generated.resources.ic_plus
import japp.composeapp.generated.resources.ic_recycle
import japp.composeapp.generated.resources.ic_target
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CardViewJaap(item: CountingDetails, darkMode: Boolean , onDelete: () -> Unit , onContinue: () -> Unit) {
    val isMenuExpanded = remember { mutableStateOf(false) }

    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if (darkMode) Color.Black else Color.White,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier.width(80.dp)
                        .background(
                            color = if (darkMode) Color(0XFF2c2c2c) else Orange,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.totalCount.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.countTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!darkMode) Color(0xFF87490C) else Color.White
                    )
                    Row(modifier = Modifier.wrapContentWidth()) {

                        Image( modifier = Modifier.size(24.dp).padding(start = 10.dp),
                            painter = painterResource(Res.drawable.ic_target),
                            contentDescription = "target",
                            colorFilter = ColorFilter.tint(Color.Red))

                        Text(modifier = Modifier.padding(start = 10.dp),
                            color =  if (darkMode)Color.White else Color.Black,
                            fontSize = 14.sp,
                            text = "${item.target}")

                        Image( modifier = Modifier.size(24.dp).padding( start = 10.dp),
                            painter = painterResource(Res.drawable.ic_mala),
                            contentDescription = "mala",
                            colorFilter = ColorFilter.tint(if (darkMode)Color.White else Color.Black))

                        Text(modifier = Modifier.padding(horizontal = 10.dp),
                            color = if (darkMode)Color.White else Color.Black ,
                            fontSize = 14.sp,
                            text = (item.totalCount / item.target).toString())

                    }

                    Text(
                        text = item.countDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { isMenuExpanded.value = true },
                        tint = if (!darkMode) Color(0xFF87490C) else Color.Gray
                    )

                    DropdownMenu(
                        modifier = Modifier.background(if (darkMode) Color.DarkGray else Color.White),
                        expanded = isMenuExpanded.value,
                        onDismissRequest = { isMenuExpanded.value = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            onContinue()
                            isMenuExpanded.value = false
                        }) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                Box(modifier = Modifier.wrapContentSize().padding(end = 8.dp)) {
                                    Image(
                                        modifier = Modifier.size(24.dp),
                                        contentDescription = "Plus",
                                        painter = painterResource(Res.drawable.ic_plus)
                                    )
                                    Image(
                                        modifier = Modifier.size(24.dp),
                                        contentDescription = "recycle",
                                        painter = painterResource(Res.drawable.ic_recycle)
                                    )
                                }

                                Text(
                                    modifier = Modifier.padding(end = 15.dp), color = PureOrange,
                                    text = stringResource(Res.string.contin)
                                )
                            }
                        }
                        DropdownMenuItem(onClick = {
                            // Handle delete action
                            isMenuExpanded.value = false
                            onDelete()
                        }) {
                            Row(modifier = Modifier.fillMaxSize()) {

                                Image(
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Plus",
                                    painter = painterResource(Res.drawable.ic_delete)
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp, end = 15.dp),
                                    color = Color(0xFFf36464),
                                    text = stringResource(Res.string.delete)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp).background(Color.Transparent))
            Spacer(modifier = Modifier.fillMaxWidth().height(0.4.dp).background(Color.DarkGray))
        }
    }
}
