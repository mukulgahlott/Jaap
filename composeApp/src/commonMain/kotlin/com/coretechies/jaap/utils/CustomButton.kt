import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackCompose.ui.theme.Orange
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun customButtons(backgroundColor: MutableState<Color>, icon: DrawableResource) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(50.dp)
            .clickable {
                backgroundColor.value = if (backgroundColor.value == Color(0XFF49494972)) {
                    Orange
                } else {
                    Color(0XFF49494972)
                }

            }
    ) {
        Surface(
            shape = CircleShape,
            color = backgroundColor.value,
            modifier = Modifier.size(50.dp)
        ) {
        }
        Image(
            painter = painterResource(icon),
            contentDescription = "Circular Image",
            modifier = Modifier.size(30.dp)
        )
    }
}
