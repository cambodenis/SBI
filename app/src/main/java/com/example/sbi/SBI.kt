package com.example.sbi

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sbi.custom.BottomSheetScaffold
import com.example.sbi.custom.rememberBottomSheetScaffoldState
import com.example.sbi.navigation.AllScreens
import com.example.sbi.navigation.BottomMenuGraph
import com.example.sbi.navigation.BottomNavHost
import com.example.sbi.navigation.Home
import com.example.sbi.navigation.MainScreens
import com.example.sbi.navigation.SettingsScreens
import com.example.sbi.navigation.navigateToMain
import com.example.sbi.screens.topAppBar
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.SBITheme
import com.example.sbi.ui.theme.White
import com.example.sbi.utils.MenuHeight
import com.example.sbi.utils.getDeviceType
import com.example.sbi.utils.windowWidth


class SBI : ComponentActivity() {
    //Global variable Windows sizes
    companion object {
        //var windowWidth = 0.dp
        //var windowHeight = 0.dp
        //var innerPaddings = Modifier.padding(0.dp)
        //var orientation = 0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SBITheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                val currentScreen = AllScreens.find { it.route == currentDestination?.route } ?: Home
                val scaffoldState = rememberBottomSheetScaffoldState()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor,contentColor= White
                ) {
                    //Deleted before build
                    var timesRequested = rememberSaveable(Int) {
                        0
                    }
                    timesRequested++
                    Log.i("Phone Count", "$timesRequested")

                    //Deleted before build
                    BoxWithConstraints() {
                        //Start Save global variable Windows sizes
                        getDeviceType(this.maxWidth,this.maxHeight, LocalConfiguration.current.orientation)
                        //End Save global variable Windows sizes

                      BottomSheetScaffold(
                          topBar = {
                              topAppBar()
                          },
                          sheetContent = {
                              BottomMenuGraph(
                                  mainScreens = MainScreens,
                                  settingsScreens = SettingsScreens,
                                  onSelected = { screen ->
                                      navController.navigateToMain(screen.route)
                                  },
                                  currentScreen = currentScreen,
                              )

                          },

                          scaffoldState = scaffoldState,
                          sheetPeekHeight = MenuHeight + 10.dp,
                          sheetBackgroundColor = Color.Transparent,
                          sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
                          modifier = Modifier.widthIn(max = windowWidth, min = windowWidth),
                          )
                      { innerPadding ->
                         //innerPaddings = Modifier.padding(innerPadding)
                          BottomNavHost(
                              navController = navController,
                              modifier = Modifier.padding(innerPadding)
                          )
                      }
                    }

                }
            }
        }
        hideSystemUI()
    }

    private fun hideSystemUI() {

        actionBar?.hide()
        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(
            window,
            true
        ) ///If False ignor bars if true offset to bar
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        } else {
            window.insetsController?.apply {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}