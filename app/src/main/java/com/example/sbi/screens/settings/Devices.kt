package com.example.sbi.screens.settings

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.sbi.database.DeviceItem
import com.example.sbi.database.DeviceViewModel
import com.example.sbi.database.DeviceViewModelFactory
import com.example.sbi.navigation.navigateToMain
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.White


@Composable
fun Settings_Devices(navController: NavHostController ) {
    val context = LocalContext.current
    val mDeviceViewModel: DeviceViewModel =
        viewModel(factory = DeviceViewModelFactory(context.applicationContext as Application))
    val devices = mDeviceViewModel.readAllData.collectAsState(listOf()).value


    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()

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
                mDeviceViewModel = mDeviceViewModel,
                navController = navController,
                 modifier = Modifier.padding(innerPadding)
            )
        }


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DeviceList(
    deviceList: List<DeviceItem>,
    mDeviceViewModel: DeviceViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {


    FlowRow(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 6,
    ) {
        deviceList.forEach { deviceItem ->
            val deviceId = 5
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .border(width = 1.dp, color = White)
                    .clickable {
                        navController.navigate("SettingsDevice?deviceId=${deviceItem.deviceId}")
/*
                        navController.navigate(
                            SettingsScreens.Device.routeWithArgs(
                                deviceItem.deviceId.toString()
                            )
                        )

 */
                    }
            ) {
                Box(
                    modifier = Modifier

                        // .fillMaxSize()
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                    //.background(color = deviceItem.deviceColorIcon)
                )
                Column(
                    modifier = Modifier
                        // .fillMaxSize()
                        .padding(5.dp, 10.dp, 5.dp, 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = deviceItem.deviceId.toString() + deviceItem.deviceName,
                        textAlign = TextAlign.Center,
                        color = White,
                        fontSize = 30.sp,
                        modifier = Modifier

                    )
                    Text(
                        text = deviceItem.deviceType,
                        textAlign = TextAlign.Center,
                        color = White,
                        fontSize = 30.sp,
                        modifier = Modifier

                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(deviceItem.deviceIcon)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(size = 50.dp),
                        colorFilter = ColorFilter.tint(Color(deviceItem.deviceColorIcon)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "${deviceItem.deviceData} ${deviceItem.deviceDataUnits}",
                        textAlign = TextAlign.Center,
                        color = White,
                        fontSize = 25.sp,
                        modifier = Modifier

                    )

                }
            }

        }
    }


}