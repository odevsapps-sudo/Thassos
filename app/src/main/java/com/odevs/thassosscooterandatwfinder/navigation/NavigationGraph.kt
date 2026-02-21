package com.odevs.thassosscooterandatwfinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.odevs.thassosscooterandatwfinder.data.FakeBeachesProvider
import com.odevs.thassosscooterandatwfinder.screens.*
import com.odevs.thassosscooterandatwfinder.data.FakeTripsProvider
import com.odevs.thassosscooterandatwfinder.util.LanguageManager
import com.odevs.thassosscooterandatwfinder.screens.ScooterScreen
import com.odevs.thassosscooterandatwfinder.ui.gettingthere.GettingThereScreen

@Composable
fun NavigationGraph(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val currentLang = remember { LanguageManager.getSelectedLanguage(context) }


    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                onCardClick = { route ->
                    navController.navigate(route)
                }
            )
        }
                composable("atws") { ATWsScreen() }
        composable("settings") { SettingsScreen() }
        composable("restaurants") { RestaurantsScreen() }
        composable("beaches") {
            BeachesScreen(
                beaches = FakeBeachesProvider.beachList,
                trips = FakeTripsProvider.tripList
            )
        }
        composable("scooterlist") { ScooterListScreen() }
        composable("vehicles") {
            ScooterScreen(navController)
        }
        composable("ferries") {
            FerryScreen()
        }
        composable("map") {
            MapScreen()
        }
        composable("kavala_highlights") {
            KavalaHighlightsScreen(navController)
        }
        composable("reach_thassos") {
            GettingThereScreen()
        }
    }
}