package com.example.sbi.utils


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sbi.R
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.White


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPicker(
    colors: List<Color?>,
    iconColor: Color?,
    selectedIconColor: (color: Color?) -> Unit,
    deviceColorIndicator: Color?,
    selectedDeviceColorIndicator: (color: Color?) -> Unit,
    deviceColorAlarm: Color?,
    selectedDeviceColorAlarm: (color: Color?) -> Unit,
    onDismissRequest: () -> Unit, // Cancel Request
    modifier: Modifier = Modifier
)
{
    Column(modifier = modifier
        .fillMaxWidth(0.5f)
        .fillMaxHeight(0.8f)
        .border(1.dp, White)
        .background(BackgroundColor)
        .padding(20.dp)) {
  Text(text = stringResource(R.string.icon_color))
            FlowRow(
            ) {
                colors.distinct().forEach { color ->
                    ColorItem(
                        selected = color == iconColor,
                        color = color,
                        onClick = { selectedIconColor(color) }
                    )
                }
            }



        Text(text = stringResource(R.string.icon_indicator_color))
        FlowRow(
        ) {
            colors.distinct().forEach { color ->
                ColorItem(
                    selected = color == deviceColorIndicator,
                    color = color,
                    onClick = { selectedDeviceColorIndicator(color) }
                )
            }
        }
        Text(text = stringResource(R.string.alarm_color))
        FlowRow(
        ) {
            colors.distinct().forEach { color ->
                ColorItem(
                    selected = color == deviceColorAlarm,
                    color = color,
                    onClick = { selectedDeviceColorAlarm(color) }
                )
            }
        }
        OutlinedButton(onClick = {
            onDismissRequest()
        }, modifier = Modifier.padding(10.dp))
        {
            Text(text = stringResource(R.string.Save), style = TextStyle(
                color = White, fontSize = 15.sp
            )
            )
        }
    }
}

@Composable
fun ColorItem(
    selected: Boolean,
    color: Color?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .requiredSize(40.dp)
            .clickable(onClick = onClick)
    ) {
        if (color != null) {
            // Transparent background pattern
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .fillMaxHeight()
                    .background(BackgroundColor)
            )
            // Color indicator
            val colorModifier =
                if (color.luminance() < 0.1 || color.luminance() > 0.9) {
                    Modifier
                        .fillMaxSize()
                        .background(color)
                        .border(
                            width = 1.dp,
                            color = White,
                            shape = CircleShape
                        )
                } else {
                    Modifier
                        .fillMaxSize()
                        .background(color)
                }
            Box(
                modifier = colorModifier
            ) {
                if (selected) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = Icons.Default.Check.name,
                        tint = if (color.luminance() < 0.5) Color.White else Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        } else {
            if (selected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .background(BackgroundColor)
                )
            }
            // Color null indicator
            Icon(
                painter = painterResource(id = R.drawable.ic_color_off),
                contentDescription = Icons.Default.Clear.name,
                modifier = Modifier.align(Alignment.Center),
                tint = contentColorFor(White)
            )
        }
    }
}