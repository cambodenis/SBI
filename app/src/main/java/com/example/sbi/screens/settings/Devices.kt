package com.example.sbi.screens.settings

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sbi.database.DeviceItem
import com.example.sbi.database.DeviceViewModel
import com.example.sbi.database.DeviceViewModelFactory
import com.example.sbi.navigation.navigateToMain
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.White
import com.example.sbi.utils.DeviceView


@Composable
fun Settings_Devices(navController: NavHostController ) {
    val context = LocalContext.current
    val mDeviceViewModel: DeviceViewModel = viewModel(factory = DeviceViewModelFactory(context.applicationContext as Application))
    val devices = mDeviceViewModel.readAllData.collectAsState(listOf()).value
    BoxWithConstraints(){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize().padding(5.dp)

        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(modifier = Modifier.border(
                        width = 1.dp,
                        color = White,
                        shape = RoundedCornerShape(10.dp),
                    ).background(color = BackgroundColor), containerColor = Color.Transparent, onClick = {
                        navController.navigateToMain("SettingsDevice")
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = Color.White,
                        )
                    }
                }) {innerPadding ->
                DeviceList(
                    deviceList = devices,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }


        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DeviceList(
    deviceList: List<DeviceItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val coroutine = rememberCoroutineScope()
    val settingViewModel: SettingViewModel = viewModel()
    FlowRow(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 6,
    ) {
        deviceList.forEach { deviceItem ->

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .border(width = 1.dp, color = White)
                    .clickable {
                        navController.navigate("SettingsDevice?deviceId=${deviceItem.deviceId}")
                    }
            ) {
                DeviceView(deviceItem)
            }

        }
    }


}