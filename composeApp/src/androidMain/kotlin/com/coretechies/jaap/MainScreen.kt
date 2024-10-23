import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coretechies.jaap.R
import com.coretechies.jaap.screens.ListScreen
import com.example.jetpackCompose.ui.theme.Orange

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
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.List,
        BottomNavItem.Menu
    )

    // Observe the backstack entry as state to correctly track the current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar( modifier = Modifier.height(70.dp), containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White) {
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
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
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
