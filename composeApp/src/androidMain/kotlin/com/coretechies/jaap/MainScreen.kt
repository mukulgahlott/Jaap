import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coretechies.jaap.R

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

sealed class BottomNavItem(val route: String, val iconRes: Int, val title: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object List : BottomNavItem("list", R.drawable.list, "List")
    object Menu : BottomNavItem("menu", R.drawable.menu, "Menu")
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.List,
        BottomNavItem.Menu
    )

    // Observe the backstack entry as state to correctly track the current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(  containerColor = Color.Black  ) {
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            NavigationBarItem(

                icon = {
                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        // Apply tint based on selection state
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                            if (isSelected) Color.Red else Color.Gray
                        )
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.Red else Color.Gray // Change text color on selection
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
                    selectedIconColor = Color.Red,       // Selected icon color
                    unselectedIconColor = Color.Gray,    // Unselected icon color
                    indicatorColor = Color.Transparent   // Transparent indicator color
                )
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: androidx.navigation.NavHostController, modifier: Modifier) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier.background(if (isSystemInDarkTheme()) Color.Black else Color.White)
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.List.route) {
            ListScreen()
        }
        composable(BottomNavItem.Menu.route) {
            MenuScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "Home Screen")
    }
}

@Composable
fun ListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "List Screen")
    }
}

@Composable
fun MenuScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "Menu Screen")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier, color = Color.Red)
}

@Composable
fun GreetingPreview() {
    Greeting("Android")
}
