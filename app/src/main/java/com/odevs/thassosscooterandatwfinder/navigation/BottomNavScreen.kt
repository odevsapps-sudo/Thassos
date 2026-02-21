package com.odevs.thassosscooterandatwfinder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(val route: String, val icon: ImageVector, val label: String) {
    object Scooters : BottomNavScreen("scooters", Icons.Default.TwoWheeler, "Scooters")
    object ATWs : BottomNavScreen("atws", Icons.Default.DirectionsCar, "ATWs")
    object ContactUs : BottomNavScreen("contact", Icons.Default.Phone, "Contact us")
    object Settings : BottomNavScreen("settings", Icons.Default.Settings, "Settings")
}
