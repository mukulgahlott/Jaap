import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.user_3__1
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuScreen() {

    val moonBackgroundColor = remember { mutableStateOf(Color(0XFF49494972)) }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(vertical = 20.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            profileButtons(Res.drawable.user_3__1)

            Text(
                modifier = Modifier.wrapContentHeight(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF87490c),
                text = "Digital Mala Jaap",
                textAlign = TextAlign.Center
            )

            customButtons(moonBackgroundColor, Res.drawable.moon_stars)

        }
    }
}

@Composable
fun profileButtons(icon: DrawableResource) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)
    ) {
        Surface(
            shape = CircleShape, color = Color(0XFF49494972), modifier = Modifier.size(50.dp)
        ) {}
        Image(
            painter = painterResource(icon),
            contentDescription = "Circular Image",
            modifier = Modifier.size(30.dp)
        )
    }
}

