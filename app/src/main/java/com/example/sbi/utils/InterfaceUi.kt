package com.example.sbi.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sbi.SBI.Companion.windowWidth
import com.example.sbi.ui.theme.White


@Composable
fun card(
    deviceName: String,
    deviceData: Double,
    deviceDataMin: Double,
    deviceDataMax: Double,
    deviceDataUnits: String,
    deviceColorIcon: Color,
    deviceColorIndicator: Color,
    deviceIcon: String,
    deviceType: Int
) {
    Column(
        modifier = Modifier.border(
            width = 0.5.dp,
            color = White,
            shape = RoundedCornerShape(size = 10.dp)
        )
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
        AsyncImage( deviceIcon, deviceColorIcon, 0.8f)
        Text(
            text = "$deviceData $deviceDataUnits",
            textAlign = TextAlign.Center,
            color = White, fontSize = 24.sp
        )
    }

}


@Composable
fun widget(
    deviceName: String,
    deviceData: Double,
    deviceDataMin: Double,
    deviceDataMax: Double,
    deviceDataUnits: String,
    deviceColorIcon: Color,
    deviceColorIndicator: Color,
    deviceIcon: String,
    deviceType: Int


) {

    val backgroundColor = Brush.horizontalGradient(
        listOf(
            deviceColorIcon, deviceColorIndicator
        )
    )
    val remaining = (deviceDataMax * deviceData) / 100
    Text(
        text = "$deviceType",
        textAlign = TextAlign.Center,
        color = White,
        fontSize = 25.sp,
        modifier = Modifier

    )
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxWidth(0.7f).height(400.dp)
            .border(width = 1.dp, color = White, shape = RoundedCornerShape(size = 10.dp))


    ) {
        Box(
            modifier = Modifier.height((400 * deviceData / 100).dp).fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                .background(backgroundColor)
        )
        Column(
            modifier = Modifier.height(300.dp).fillMaxWidth().padding(5.dp, 10.dp, 5.dp, 10.dp),
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
            AsyncImage( deviceIcon, deviceColorIcon, 0.8f)

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
}