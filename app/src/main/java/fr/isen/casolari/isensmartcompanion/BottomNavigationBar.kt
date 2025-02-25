package fr.isen.casolari.isensmartcompanion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState



// ----------------------------------------
// This is a wrapper view that allows us to easily and cleanly
// reuse this component in any future project
@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var items = listOf(
        Screen.Home,
        Screen.Event,
        Screen.History
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach{ screen ->
            NavigationBarItem(
                selected = (currentRoute == screen.route),
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (screen){
                        is Screen.Home -> Icon(Icons.Default.Home, contentDescription = screen.title)
                        is Screen.Event -> Icon(Icons.Default.DateRange, contentDescription = screen.title)
                        is Screen.History -> Icon(Icons.Default.Search, contentDescription = screen.title)
                    }
                },
                label = { Text(screen.title)}
            )
        }
    }
}