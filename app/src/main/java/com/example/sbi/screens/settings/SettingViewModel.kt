package com.example.sbi.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sbi.ui.theme.Orange
import com.example.sbi.ui.theme.Red
import com.example.sbi.ui.theme.White
import kotlinx.coroutines.launch

data class SingleDevice(
   // val fsdfsdfsd: DeviceItem,
    val deviceId: Int? = null,
    val deviceGateAddress: Int = 0,
    val deviceAddress: Int = 0,
    val deviceName: String = "Name",
    val deviceType: String = "0",
    val deviceData: Double = 0.0,
    val deviceDataMax: Double = 0.0,
    val deviceDataMin: Double = 0.0,
    val deviceDataUnits: String = "unit",
    val deviceColorIcon: Int = White.toArgb(),
    val deviceColorIndicator: Int = Orange.toArgb(),
    val deviceColorAlarm: Int = Red.toArgb(),
    val deviceIcon: String = "file:///android_asset/icon/AC.svg",
    val deviceTopBar: Boolean = false
)

/**
 * ViewModel containing the app data and methods to process the data
 */
class SettingViewModel : ViewModel() {

    var uiState by mutableStateOf(SingleDevice())
        private set // the setter is private and has the default implementatio

    fun updateSingleDevice(updatedDeviceItem: SingleDevice) {

        viewModelScope.launch {
            uiState = uiState.copy(

                deviceId = updatedDeviceItem.deviceId,
                deviceGateAddress = updatedDeviceItem.deviceGateAddress,
                deviceAddress = updatedDeviceItem.deviceAddress,
                deviceName = updatedDeviceItem.deviceName,
                deviceType = updatedDeviceItem.deviceType,
                deviceData = 65.00,
                deviceDataMax = updatedDeviceItem.deviceDataMax,
                deviceDataMin = updatedDeviceItem.deviceDataMin,
                deviceDataUnits = updatedDeviceItem.deviceDataUnits,
                deviceColorIcon = updatedDeviceItem.deviceColorIcon,
                deviceColorIndicator = updatedDeviceItem.deviceColorIndicator,
                deviceColorAlarm = updatedDeviceItem.deviceColorAlarm,
                deviceIcon = updatedDeviceItem.deviceIcon,
                deviceTopBar = updatedDeviceItem.deviceTopBar
            )
        }
    }
}