package com.example.sbi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sbi.screens.main.Home_Screen
import com.example.sbi.screens.main.Light_Screen
import com.example.sbi.screens.main.Pump_Screen
import com.example.sbi.screens.main.Run_Screen
import com.example.sbi.screens.main.Utilites_Screen
import com.example.sbi.screens.settings.Settings_Device
import com.example.sbi.screens.settings.Settings_Devices
import com.example.sbi.screens.settings.Settings_Screen

//https://medium.com/@daniel.atitienei/navigate-with-arguments-in-jetpack-compose-90846d70bb7f

@Composable
fun BottomNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SettingsDevices.route,
        modifier = modifier
    ) {
        composable(route = Run.route) {
            Run_Screen()
        }
        composable(route = Pump.route) {
            Pump_Screen()
        }
        composable(route = Home.route) {
            Home_Screen(navController = navController)
        }
        composable(route = Utilites.route) {
            Utilites_Screen()
        }
        composable(route = Light.route) {
            Light_Screen()
        }
        composable(route = Settings.route) {
            Settings_Screen()
        }
        composable(route = SettingsDevices.route) {
            Settings_Devices(navController = navController
            )
        }
        composable(
            "SettingsDevice?deviceId={deviceId}",
            arguments = listOf(
                navArgument("deviceId") {
                    nullable = true
                    //defaultValue = null
                    type = NavType.StringType
                }

            )
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            Settings_Device(navController, deviceId)
        }

    }
}

fun NavHostController.navigateToMain(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateToMain.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }


fun NavHostController.navigateToDevice(deviceId: String) {
    this.navigateToMain("${SettingsDevice.route}?deviceId=$deviceId")
}