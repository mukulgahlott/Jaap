import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coretechies.jaap.R
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails
import com.coretechies.jaap.screens.ListScreen
import com.example.jetpackCompose.ui.theme.Orange
import kotlinx.coroutines.flow.map


@Composable
fun MainScreen(prefs: DataStore<Preferences>, countingDao: CountingDao) {

    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        bottomBar = { BottomNavigationBar(navController = navController, prefs) }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            prefs = prefs,
            countingDao
        )
    }
}

sealed class BottomNavItem(val route: String, val iconRes: Int, val title: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object List : BottomNavItem("list", R.drawable.list, "List")
    object Menu : BottomNavItem("menu", R.drawable.menu, "Menu")
}

@Composable
fun BottomNavigationBar(navController: NavHostController, prefs: DataStore<Preferences>) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.List,
        BottomNavItem.Menu
    )

    // Observe the backstack entry as state to correctly track the current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Observe the dark mode preference as state
    val darkMode by prefs
        .data
        .map {
            val darkModeKey = booleanPreferencesKey("DarkMode")
            it[darkModeKey] ?: false
        }.collectAsState(false)



    Column {
        // Thin line at the top of the bottom bar
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.3.dp)
                .background(if(!darkMode)Color(0xFFcfb69e) else Color(0XFF2c2c2c))
        )

        NavigationBar(
            modifier = Modifier.wrapContentHeight(),
            containerColor = if (darkMode) Color.Black else Color.White
        ) {
            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true

                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            colorFilter = ColorFilter.tint(
                                if (isSelected) Orange else Color(0XFF2c2c2c)
                            )
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (isSelected) Orange else Color(0XFF2c2c2c)
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Orange,
                        unselectedIconColor = Color(0XFF2c2c2c),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier,
    prefs: DataStore<Preferences>,
    countingDao: CountingDao
) {

    var countingData by remember { mutableStateOf<CountingDetails?>(null) }

    val darkMode by prefs
        .data
        .map {
            val darkModeKey = booleanPreferencesKey("DarkMode")
            it[darkModeKey] ?: false
        }.collectAsState(false)

    NavHost(
        navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier.background(if (darkMode) Color.Black else Color.White)
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(LocalContext.current, prefs, countingDao, countingData, onDiscontinue = {
                countingData = null
            })
        }
        composable(BottomNavItem.List.route) {
            ListScreen(prefs, countingDao) { data ->
                countingData = data
                navController.navigate(BottomNavItem.Home.route)
            }
        }
        composable(BottomNavItem.Menu.route) {
            MenuScreen(LocalContext.current, prefs)
        }
    }
}
