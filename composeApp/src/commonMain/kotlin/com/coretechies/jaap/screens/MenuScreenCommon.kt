import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import japp.composeapp.generated.resources.contact_us
import japp.composeapp.generated.resources.ic_bell
import japp.composeapp.generated.resources.ic_coretechies
import japp.composeapp.generated.resources.ic_document
import japp.composeapp.generated.resources.ic_envelop
import japp.composeapp.generated.resources.ic_language
import japp.composeapp.generated.resources.ic_lock
import japp.composeapp.generated.resources.ic_rate
import japp.composeapp.generated.resources.ic_share
import japp.composeapp.generated.resources.language
import japp.composeapp.generated.resources.language_name
import japp.composeapp.generated.resources.moon_stars
import japp.composeapp.generated.resources.notification
import japp.composeapp.generated.resources.notification_enabled
import japp.composeapp.generated.resources.privacy
import japp.composeapp.generated.resources.rate_us
import japp.composeapp.generated.resources.share_app
import japp.composeapp.generated.resources.terms
import japp.composeapp.generated.resources.user_3__1
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MenuScreen() {

    val moonBackgroundColor = remember { mutableStateOf(Color(0xFFb7926d)) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = "Menu",
                textAlign = TextAlign.Center
            )

            customButtons(moonBackgroundColor, Res.drawable.moon_stars)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            RenderCustomButton(
                showSwitch = true,
                icon = painterResource(Res.drawable.ic_bell),
                title = stringResource(Res.string.notification),
                description = stringResource(Res.string.notification_enabled),
                topMargin = 5.dp,
                showDescription = true,
                modifier = Modifier,
                onClick = {

                },
            )

            RenderCustomButton(
                showSwitch = false,
                icon = painterResource(Res.drawable.ic_language),
                title = stringResource(Res.string.language),
                description = stringResource(Res.string.language_name),
                topMargin = 5.dp,
                showDescription = true,
                modifier = Modifier,
                onClick = {

                },
            )

            RenderCustomButton(
                showSwitch = false,
                icon = painterResource(Res.drawable.ic_share),
                title = stringResource(Res.string.share_app),
                description = stringResource(Res.string.share_app),
                topMargin = 24.dp,
                showDescription = false,
                modifier = Modifier,
                onClick = {

                },
            )

            RenderCustomButton(
                showSwitch = false,
                icon = painterResource(Res.drawable.ic_rate),
                title = stringResource(Res.string.rate_us),
                description = stringResource(Res.string.rate_us),
                topMargin = 5.dp,
                showDescription = false,
                modifier = Modifier,
                onClick = {

                },
            )

            RenderCustomButton(
                showSwitch = false,
                icon = painterResource(Res.drawable.ic_envelop),
                title = stringResource(Res.string.contact_us),
                description = stringResource(Res.string.contact_us),
                topMargin = 5.dp,
                showDescription = false,
                modifier = Modifier,
                onClick = {

                },
            )

            RenderCustomButton(
                showSwitch = false,
                icon = painterResource(Res.drawable.ic_document),
                title = stringResource(Res.string.terms),
                description = stringResource(Res.string.terms),
                topMargin = 24.dp,
                showDescription = false,
                modifier = Modifier,
                onClick = {

                },
            )

            RenderCustomButton(
                showSwitch = false,
                icon = painterResource(Res.drawable.ic_lock),
                title = stringResource(Res.string.privacy),
                description = stringResource(Res.string.privacy),
                topMargin = 5.dp,
                showDescription = false,
                modifier = Modifier,
                onClick = {

                },
            )

            Image(
                painter = painterResource(Res.drawable.ic_coretechies),
                contentDescription = "CoreTechies Icon",
                modifier = Modifier.fillMaxWidth().height(140.dp)
                    .padding(top = 54.dp, bottom = 24.dp),
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
fun profileButtons(icon: DrawableResource) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)
    ) {
        Surface(
            shape = CircleShape, color = Color(0xFFb7926d), modifier = Modifier.size(50.dp)
        ) {}
        Image(
            painter = painterResource(icon),
            contentDescription = "Circular Image",
            modifier = Modifier.size(30.dp)
        )
    }
}

