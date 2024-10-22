import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import japp.composeapp.generated.resources.Res
import japp.composeapp.generated.resources.bg_dark
import japp.composeapp.generated.resources.bg_light
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview



@Composable
fun FullScreenBackground(content: @Composable () -> Unit) {


    Box(
        modifier = Modifier.fillMaxSize(),

    ) {
        // Background Image
        Image(
            painter = if(isSystemInDarkTheme()) painterResource(Res.drawable.bg_dark)
            else painterResource(Res.drawable.bg_light),
            contentScale = ContentScale.FillBounds,

            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )

        // Content overlay
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            contentAlignment = Alignment.Center // Center content if needed
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewFullScreenBackground() {
    FullScreenBackground {
        // Place your main content here
        Text("Hello World!", color = Color.White)
    }
}
