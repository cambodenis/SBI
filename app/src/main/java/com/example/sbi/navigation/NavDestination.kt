package com.example.sbi.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.sbi.R


/**
 * Contract for information needed on every Rally navigation destination
 */

interface BottomNavDestination {
    val icon: Int
    val route: String
    val label: Int
}

object Home : BottomNavDestination {
    override val icon = R.drawable.menu_home
    override val route = "Home"
    override val label = R.string.menu_home
}

object Light : BottomNavDestination {
    override val icon = R.drawable.menu_light
    override val route = "Light"
    override val label = R.string.menu_light
}

object Pump : BottomNavDestination {
    override val icon = R.drawable.menu_pump
    override val route = "Pump"
    override val label = R.string.menu_pump
}

object Run : BottomNavDestination {
    override val icon = R.drawable.menu_run
    override val route = "Run"
    override val label = R.string.menu_run
}

object Utilites : BottomNavDestination {
    override val icon = R.drawable.menu_utilites
    override val route = "Utilites"
    override val label = R.string.menu_utilites
}

object Settings : BottomNavDestination {
    override val icon = R.drawable.menu_utilites
    override val route = "Settings"
    override val label = R.string.menu_settings
}

object SettingsDevices : BottomNavDestination {
    override val icon = R.drawable.menu_utilites
    override val route = "Devices"
    override val label = R.string.menu_devices
}

object SettingsDevice : BottomNavDestination {
    // Added for simplicity, this icon will not in fact be used, as Settings isn't
    // part of the RallyTabRow selection
    override val icon = R.drawable.menu_settings
    override val route = "Device"
    override val label = R.string.menu_settings
    const val deviceId = "-1"
    val routeWithArgs = "$route/{$deviceId}"
    val arguments = listOf(navArgument(deviceId) { type = NavType.StringType })
}

// Screens to be displayed in the bottom menu bar
val AllScreens= listOf(
    Run,
    Pump,
    Home,
    Utilites,
    Light,
    Settings,
    SettingsDevices,
    SettingsDevice,
)
val MainScreens = listOf(
    Run,
    Pump,
    Home,
    Utilites,
    Light,
)

val SettingsScreens = listOf(
    Settings,
    SettingsDevices,
)