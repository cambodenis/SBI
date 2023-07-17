package com.example.sbi.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sbi.database.DeviceViewModel
import com.example.sbi.database.DeviceViewModelFactory
import com.example.sbi.ui.theme.BorderColorOneWay
import com.example.sbi.ui.theme.BorderColorTwoWay
import com.example.sbi.ui.theme.BorderSize
import com.example.sbi.ui.theme.White
import com.example.sbi.utils.AsyncImage
import com.example.sbi.utils.ClockText
import com.example.sbi.utils.DisplaySize
import com.example.sbi.utils.TopBarFontStyle
import com.example.sbi.utils.TopBarHeight
import com.example.sbi.utils.typeOfScreen


@Composable
fun topAppBar() {
    val context = LocalContext.current
    val mDeviceViewModel: DeviceViewModel =
        viewModel(factory = DeviceViewModelFactory(context.applicationContext as Application))
             val topBarIcon = mDeviceViewModel.getDeviceTopBarIcon(true).collectAsState(listOf()).value


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(TopBarHeight)
            .drawBehind {
                drawLine(
                    brush = Brush.horizontalGradient(colors = BorderColorTwoWay),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = BorderSize.toPx()
                )
            }.padding(5.dp),
    ) {
        if (typeOfScreen != DisplaySize.Compact) topBarWeather()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp, 5.dp, 10.dp, 5.dp)
        )
        {


            topBarIcon.forEach { deviceItem ->

                var deviceGateAddress by remember { mutableStateOf(deviceItem.deviceGateAddress) }
                var deviceAddress by remember { mutableStateOf(deviceItem.deviceAddress) }
                var deviceName by remember { mutableStateOf(deviceItem.deviceName) }
                var deviceType by remember { mutableStateOf(deviceItem.deviceType.toInt()) }
                val deviceData by remember { mutableStateOf(65.00) }
                var deviceDataMax by remember { mutableStateOf(deviceItem.deviceDataMax) }
                var deviceDataMin by remember { mutableStateOf(deviceItem.deviceDataMin) }
                var deviceDataUnits by remember { mutableStateOf(deviceItem.deviceDataUnits) }
                var deviceColorIcon by remember { mutableStateOf(Color(deviceItem.deviceColorIcon)) }
                var deviceColorIndicator by remember { mutableStateOf(Color(deviceItem.deviceColorIndicator)) }
                var deviceColorAlarm by remember { mutableStateOf(Color(deviceItem.deviceColorAlarm)) }
                var deviceIcon by remember { mutableStateOf(deviceItem.deviceIcon) }
                var deviceTopBar by remember { mutableStateOf(deviceItem.deviceTopBar) }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(100.dp)
                        .padding(10.dp, 5.dp, 10.dp, 5.dp)
                )
                {
                    AsyncImage(deviceIcon, deviceColorIcon, 0.9f)
                }


            }




        }
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)
        if (typeOfScreen != DisplaySize.Compact) topBarTime()

    }


}

@Composable
fun topBarWeather() {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .width(width = 150.dp)

    ) {

        AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)

        Text(
            modifier = Modifier
                .wrapContentHeight(Alignment.CenterVertically, true),
            text = "+28℃", style = TopBarFontStyle

        )
    }
    Divider(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxHeight()  //fill the max height
            .width(1.dp)
            .drawBehind {
                drawLine(
                    brush = Brush.horizontalGradient(colors = BorderColorOneWay),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = BorderSize.toPx(),
                    alpha = 10f
                )
            }
    )
}


@Composable
fun topBarTime() {
    Divider(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxHeight()  //fill the max height
            .width(1.dp)
            .drawBehind {
                drawLine(
                    brush = Brush.horizontalGradient(colors = BorderColorOneWay),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = BorderSize.toPx(),
                    alpha = 10f
                )
            }
    )
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .width(width = 150.dp)
    ) {
        ClockText()
    }
}