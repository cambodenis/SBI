package com.example.sbi

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.sbi.screens.main.topAppBar
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.SBITheme
import com.example.sbi.ui.theme.White
import com.example.sbi.utils.MenuHeight
import com.example.sbi.utils.getDeviceType
import kotlinx.coroutines.launch


class SBI : ComponentActivity() {
    //Global variable Windows sizes
    companion object {
        var windowWidth = 0.dp
        var windowHeight = 0.dp

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
                    Log.i("Phone currentScreen", "$currentScreen")
                    Log.i("Phone currentDestination", "$currentDestination")
                    Log.i("Phone navController", "$navController")
                    //Deleted before build
                    BoxWithConstraints() {
                        //Start Save global variable Windows sizes
                        windowWidth = this.maxWidth
                        windowHeight = this.maxHeight
                        //End Save global variable Windows sizes
                        getDeviceType()
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
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetSample() {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )

    // App content
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier.toggleable(
                value = skipPartiallyExpanded,
                role = Role.Checkbox,
                onValueChange = { checked -> skipPartiallyExpanded = checked }
            )
        ) {
            Checkbox(checked = skipPartiallyExpanded, onCheckedChange = null)
            Spacer(Modifier.width(16.dp))
            Text("Skip partially expanded State")
        }
        Row(
            Modifier.toggleable(
                value = edgeToEdgeEnabled,
                role = Role.Checkbox,
                onValueChange = { checked -> edgeToEdgeEnabled = checked }
            )
        ) {
            Checkbox(checked = edgeToEdgeEnabled, onCheckedChange = null)
            Spacer(Modifier.width(16.dp))
            Text("Toggle edge to edge enabled.")
        }
        Button(onClick = { openBottomSheet = !openBottomSheet }) {
            Text(text = "Show Bottom Sheet")
        }
    }

    // Sheet content
    if (openBottomSheet) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(10, 10, 10, 100) else WindowInsets(100, 100, 100, 10)
                //BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet = false
                            }
                        }
                    }
                ) {
                    Text("Hide Bottom Sheet")
                }
            }
            var text by remember { mutableStateOf("") }
            OutlinedTextField(value = text, onValueChange = { text = it })
            LazyColumn {
                items(50) {
                    ListItem(
                        headlineContent = { Text("Item $it") },
                        leadingContent = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    )
                }
            }
        }
    }
}