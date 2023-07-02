package com.example.sbi.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sbi.SBI.Companion.windowWidth
import com.example.sbi.ui.theme.Active
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.BorderColorOneWay
import com.example.sbi.ui.theme.BorderColorTwoWay
import com.example.sbi.ui.theme.BorderSize
import com.example.sbi.ui.theme.NonActive
import com.example.sbi.utils.MenuHeight
import com.example.sbi.utils.MenuItemWidth
import com.example.sbi.utils.SettingHeight
import com.example.sbi.utils.SettingItemWidth

@Composable
fun BottomMenuGraph(
    mainScreens: List<BottomNavDestination>,
    settingsScreens: List<BottomNavDestination>,
    onSelected: (BottomNavDestination) -> Unit,
    currentScreen: BottomNavDestination
) {

    if (MenuItemWidth == 0.dp) MenuItemWidth = windowWidth / mainScreens.count()
    if (SettingItemWidth == 0.dp) SettingItemWidth = windowWidth / settingsScreens.count()
    Column(
        modifier = Modifier
            .height(MenuHeight + SettingHeight)
            .width(windowWidth)
            .background(Color.Transparent), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier.width(30.dp).height(3.dp).clip(shape = RoundedCornerShape(10.dp)),
            color = NonActive
        )
        Row(
            modifier = Modifier.selectableGroup().padding(top = 10.dp)
                .background(BackgroundColor)
                .drawBehind {
                    drawLine(
                        brush = Brush.horizontalGradient(colors = BorderColorTwoWay),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = BorderSize.toPx()
                    )
                }
                .height(MenuHeight)
                .width(windowWidth),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            mainScreens.forEachIndexed { index, screen ->
                MenuItem(
                    icon = screen.icon,
                    onSelected = { onSelected(screen) },
                    selected = currentScreen == screen,
                    last = mainScreens.lastIndex == index
                )
            }
        }

        Row(

            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            settingsScreens.forEachIndexed { index, screen ->
                SettingsItem(
                    label = screen.label,
                    onSelected = { onSelected(screen) },
                    selected = currentScreen == screen,
                    last = settingsScreens.lastIndex == index
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    icon: Int,
    onSelected: () -> Unit,
    selected: Boolean,
    last: Boolean
) {
    val contentColor = if (selected) Active else NonActive
    val selectitem = if (selected) Active else Color.Transparent
    Column(
        modifier = Modifier
            .let {
                if (!last) {
                    return@let it
                        .drawBehind {
                            rotate(degrees = 180F) {
                                drawLine(
                                    brush = Brush.verticalGradient(colors = BorderColorOneWay),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = BorderSize.toPx(),
                                )
                            }
                        }

                }
                it
            }
            .width(MenuItemWidth)
            .fillMaxHeight().padding(5.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {
        Row(
            modifier = Modifier
                .drawBehind {
                    drawLine(
                        color = selectitem,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.5.dp.toPx(),
                    )
                }
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "drawable/icon",
                tint = contentColor
            )
        }
    }

}


@Composable
private fun SettingsItem(
    label: Int,
    onSelected: () -> Unit,
    selected: Boolean,
    last: Boolean
) {
    val contentColor = if (selected) Active else NonActive
    Column(
        modifier = Modifier
            .let {
                if (!last) {
                    return@let it
                        .drawBehind {
                            rotate(degrees = 180F) {
                                drawLine(
                                    brush = Brush.verticalGradient(colors = BorderColorOneWay),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = BorderSize.toPx(),
                                )
                            }
                        }

                }
                it
            }
            .width(SettingItemWidth)
            .fillMaxHeight()
            .padding(5.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = label), color = contentColor)

        }
    }

}


