package com.example.sbi.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sbi.database.DeviceItem
import com.example.sbi.ui.theme.White

@Composable
fun DeviceView(deviceItem: DeviceItem){

    when (deviceItem.deviceType){
        "0" -> DeviceCard(
            deviceItem.deviceName,
            deviceItem.deviceIcon,
            deviceItem.deviceColorIcon,
            deviceItem.deviceColorIndicator,
            deviceItem.deviceColorAlarm,
            deviceItem.deviceData,
            deviceItem.deviceDataUnits,
            deviceItem.deviceDataMin,
            deviceItem.deviceDataMax,
            deviceItem.deviceState

        )

        "1" -> DeviceButton(
            deviceItem.deviceName,
            deviceItem.deviceIcon,
            deviceItem.deviceColorIcon,
            deviceItem.deviceColorIndicator,
            deviceItem.deviceColorAlarm,
            deviceItem.deviceData,
            deviceItem.deviceDataUnits,
            deviceItem.deviceDataMin,
            deviceItem.deviceDataMax,
            deviceItem.deviceState

        )
        "2" -> DeviceWidget(
            deviceItem.deviceName,
            deviceItem.deviceIcon,
            deviceItem.deviceColorIcon,
            deviceItem.deviceColorIndicator,
            deviceItem.deviceColorAlarm,
            deviceItem.deviceData,
            deviceItem.deviceDataUnits,
            deviceItem.deviceDataMin,
            deviceItem.deviceDataMax,
            deviceItem.deviceState

        )
    }

}
@Composable
fun DeviceCard(
    deviceName: String,
    deviceIcon: String,
    deviceColorIcon: Int,
    deviceColorIndicator: Int,
    deviceColorAlarm: Int,
    deviceData: Double,
    deviceDataUnits: String,
    deviceDataMin: Double,
    deviceDataMax: Double,
    deviceState:Boolean

) {

    Column(
        modifier = Modifier
            .width((windowWidth / 3))
            .fillMaxHeight()
            .wrapContentHeight()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = deviceName,
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 30.sp,
        )
        AsyncImage( deviceIcon, Color(deviceColorIcon), 0.8f)
        Text(
            text = "${deviceData} ${deviceDataUnits}",
            textAlign = TextAlign.Center,
            color = White, fontSize = 24.sp
        )
    }

}

@Composable
fun DeviceButton(
    deviceName: String,
    deviceIcon: String,
    deviceColorIcon: Int,
    deviceColorIndicator: Int,
    deviceColorAlarm: Int,
    deviceData: Double,
    deviceDataUnits: String,
    deviceDataMin: Double,
    deviceDataMax: Double,
    deviceState:Boolean
    ) {

    Column(
        modifier = Modifier
            .width((windowWidth / 3))
            .fillMaxHeight()
            .wrapContentHeight()
            .padding(10.dp).drawBehind {
                //need invert coordinates for rotate
                drawLine(
                    color = Color(deviceColorIndicator),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 3.dp.toPx(),
                )},
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = deviceName,
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 30.sp,
            modifier = Modifier

        )
        AsyncImage( deviceIcon, Color(deviceColorIcon), 0.8f)


    }
}


@Composable
fun DeviceWidget(
    deviceName: String,
    deviceIcon: String,
    deviceColorIcon: Int,
    deviceColorIndicator: Int,
    deviceColorAlarm: Int,
    deviceData: Double,
    deviceDataUnits: String,
    deviceDataMin: Double,
    deviceDataMax: Double,
    deviceState:Boolean
    ) {

    val backgroundColor = Brush.horizontalGradient(
        listOf(
            Color(0xFFFFFF), Color(deviceColorIndicator)
        )
    )
    val remaining = (deviceDataMax * deviceData) / 100
    Box(
        modifier = Modifier.height((400 * deviceData / 100).dp).fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .background(backgroundColor)
    )
    Column(
        modifier = Modifier
            .width((windowWidth / 3))
            .fillMaxHeight()
            .wrapContentHeight()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = deviceName,
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 30.sp,
            modifier = Modifier

        )
        AsyncImage( deviceIcon, Color(deviceColorIcon), 0.8f)

        Text(
            text = "${deviceData} %",
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 25.sp,
            modifier = Modifier

        )
        Text(
            text = "$remaining / ${deviceDataMax} ${deviceDataUnits}",
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 25.sp,
            modifier = Modifier

        )
    }
}