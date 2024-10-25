import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coretechies.jaap.R
import com.coretechies.jaap.room.counter.CountingDao
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
        NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding),prefs = prefs, countingDao)
    }
}
sealed class BottomNavItem(val route: String, val iconRes: Int, val title: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object List : BottomNavItem("list", R.drawable.list, "List")
    object Menu : BottomNavItem("menu", R.drawable.menu, "Menu")
}

@Composable
fun BottomNavigationBar(navController: NavHostController,prefs: DataStore<Preferences>) {
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

    NavigationBar( modifier = Modifier.height(70.dp), containerColor = if (darkMode) Color.Black else Color.White) {
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            NavigationBarItem(

                icon = {
                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        // Apply tint based on selection state
                        colorFilter = ColorFilter.tint(
                            if (isSelected) Orange else Color(0XFF2c2c2c)
                        )
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Orange else Color(0XFF2c2c2c) // Change text color on selection
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
                    selectedIconColor = Orange,       // Selected icon color
                    unselectedIconColor = Color(0XFF2c2c2c),    // Unselected icon color
                    indicatorColor = Color.Transparent   // Transparent indicator color
                )
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier, prefs: DataStore<Preferences>, countingDao: CountingDao) {

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
            HomeScreen(LocalContext.current,prefs,countingDao)
        }
        composable(BottomNavItem.List.route) {
            ListScreen(prefs,countingDao)
        }
        composable(BottomNavItem.Menu.route) {
            MenuScreen(prefs)
        }
    }
}
