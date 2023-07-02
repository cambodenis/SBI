package com.example.sbi.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.sbi.SBI

var typeOfScreen = ""

//Top Bar Setting
var TopBarHeight = 0.dp
var TopBarFontStyle = TextStyle()

var SettingHeight = 0.dp
var SettingItemWidth = 0.dp
var SettingMenuFontStyle = TextStyle()

//Bottom Menu Setting
var MenuHeight = 0.dp
var MenuItemWidth = 0.dp

@Composable
fun getDeviceType() {
    when {
        SBI.windowWidth < 600.dp -> {
            typeOfScreen = "Small"
            //Top Bar Setting
            TopBarHeight = 40.dp
            TopBarFontStyle = MaterialTheme.typography.labelSmall

            //Top Menu Setting
            SettingHeight = 50.dp
            SettingMenuFontStyle = MaterialTheme.typography.labelMedium

            //Bottom Menu Setting
            MenuHeight = 50.dp
            MenuItemWidth = 0.dp

        }

        SBI.windowWidth < 840.dp -> {
            typeOfScreen = "Medium"
            //Top Bar Setting
            TopBarHeight = 35.dp
            TopBarFontStyle = MaterialTheme.typography.labelMedium

            //Top Menu Setting
            SettingHeight = 50.dp
            SettingMenuFontStyle = MaterialTheme.typography.labelMedium

            //Bottom Menu Setting
            MenuHeight = 60.dp
            MenuItemWidth = 0.dp
        }

        else -> {
            typeOfScreen = "Large"
            //Top Bar Setting
            TopBarHeight = 50.dp
            TopBarFontStyle = MaterialTheme.typography.labelLarge

            //Top Menu Setting
            SettingHeight = 80.dp
            SettingMenuFontStyle = MaterialTheme.typography.labelMedium

            //Bottom Menu Setting
            MenuHeight = 100.dp
            MenuItemWidth = 100.dp
        }
    }

}
