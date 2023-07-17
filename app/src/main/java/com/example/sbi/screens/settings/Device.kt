package com.example.sbi.screens.settings

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sbi.R
import com.example.sbi.database.DeviceItem
import com.example.sbi.database.DeviceViewModel
import com.example.sbi.database.DeviceViewModelFactory
import com.example.sbi.navigation.SettingsDevices
import com.example.sbi.ui.theme.Active
import com.example.sbi.ui.theme.BorderColorOneWay
import com.example.sbi.ui.theme.BorderColorTwoWay
import com.example.sbi.ui.theme.BorderSize
import com.example.sbi.ui.theme.NonActive
import com.example.sbi.ui.theme.White
import com.example.sbi.ui.theme.userColors
import com.example.sbi.utils.ColorPicker
import com.example.sbi.utils.DisplayOrientation
import com.example.sbi.utils.ImagesPicker
import com.example.sbi.utils.card
import com.example.sbi.utils.deviceTypeGroup
import com.example.sbi.utils.getIconList
import com.example.sbi.utils.orientationOfScreen
import com.example.sbi.utils.widget


@Composable
fun Settings_Device(navController: NavHostController, id: String?) {
    val context = LocalContext.current

    val mDeviceViewModel: DeviceViewModel = viewModel(factory = DeviceViewModelFactory(context.applicationContext as Application))
    val settingViewModel: SettingViewModel = viewModel()

    if (id != null) {
       val updateDeviceItem = mDeviceViewModel.getDeviceById(id.toInt()).collectAsState(listOf()).value
        updateDeviceItem.forEach {
            Log.i("Phone Recom", "2")
            settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                it.deviceId,
                it.deviceGateAddress,
                it.deviceAddress,
                it.deviceName,
                it.deviceType,
                it.deviceData,
                it.deviceDataMax,
                it.deviceDataMin,
                it.deviceDataUnits,
                it.deviceColorIcon,
                it.deviceColorIndicator,
                it.deviceColorAlarm,
                it.deviceIcon,
                it.deviceTopBar
            ))

        }
        }

        DeviceItem(navController,mDeviceViewModel,settingViewModel.uiState)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DeviceItem(
    navController: NavHostController,
    mDeviceViewModel: DeviceViewModel,
    deviceItem:SingleDevice
) {

    val maxItemsInEachRow = if (orientationOfScreen == DisplayOrientation.Portrait.name) 1 else 2
    FlowRow(
        modifier = Modifier.fillMaxSize().padding(5.dp), maxItemsInEachRow = maxItemsInEachRow

    ) {
        if (orientationOfScreen == DisplayOrientation.Portrait.name) {
            Column(
                modifier = Modifier.weight(1f).drawBehind {
                    drawLine(
                        brush = Brush.horizontalGradient(colors = BorderColorTwoWay),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = BorderSize.toPx()
                    )
                }.fillMaxWidth().fillMaxHeight(0.3f)
            ) {Preview(navController, mDeviceViewModel)  }
            Column(modifier = Modifier.weight(1f).fillMaxSize().verticalScroll(rememberScrollState())
            ) { Setting(deviceItem) }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .weight(1f).drawBehind {
                    rotate(180f) {
                        drawLine(
                            brush = Brush.verticalGradient(colors = BorderColorTwoWay),
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = BorderSize.toPx(),
                        )
                    }
                }
            ) { Setting(deviceItem)  }
            Column(
                modifier = Modifier.weight(1f).fillMaxSize()
            ) {Preview(navController, mDeviceViewModel)}
        }
    }
}

@Composable
fun Setting(deviceItem:SingleDevice) {
    val settingViewModel: SettingViewModel = viewModel()

    var isDeviceIconVisible by remember { mutableStateOf(false) }
    var isDeviceColorFirstVisible by remember { mutableStateOf(false) }
    var isDeviceColorSecondVisible by remember { mutableStateOf(false) }
    if (isDeviceIconVisible) {
        Popup(alignment = Alignment.TopStart, onDismissRequest = {
            isDeviceIconVisible = false
        }, properties = PopupProperties(dismissOnClickOutside = false), content = {
            ImagesPicker(
                getIconList("icon")!!,
                selectedIcon = deviceItem.deviceIcon,
                onIconSelected = { selectedIcon ->
                    settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                        deviceIcon = selectedIcon
                    ))
                }) {
                isDeviceIconVisible = false
            }
        })
    }
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()
        .drawBehind {
            //need invert coordinates for rotate
            drawLine(
                brush = Brush.horizontalGradient(colors = BorderColorOneWay),
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = BorderSize.toPx(),
            )

        }
    ) {
        Column(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = stringResource(R.string.name_of_device_gate_port_and_device_port),
                    style = TextStyle(
                        color = White, fontSize = 25.sp
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(.33f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Active,
                        unfocusedBorderColor = NonActive,
                        focusedLabelColor = Active,
                        unfocusedLabelColor = NonActive,
                        focusedTextColor = Active,
                        unfocusedTextColor = NonActive

                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    ),
                    value = deviceItem.deviceName,
                    label = {
                        Text(
                            text = stringResource(R.string.device_name),
                            color = Active
                        )
                    },
                    onValueChange = {
                        settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                            deviceName = it
                        )) })
                OutlinedTextField(modifier = Modifier.fillMaxWidth(.33f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Active,
                        unfocusedBorderColor = NonActive,
                        focusedLabelColor = Active,
                        unfocusedLabelColor = NonActive,
                        focusedTextColor = Active,
                        unfocusedTextColor = NonActive

                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    ),
                    value = deviceItem.deviceGateAddress.toString(),
                    label = {
                        Text(
                            text = stringResource(R.string.gate), color = Active
                        )
                    },
                    onValueChange = {
                        settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                            deviceGateAddress = it.toInt()
                        ))
                    })
                OutlinedTextField(modifier = Modifier.fillMaxWidth(.33f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Active,
                        unfocusedBorderColor = NonActive,
                        focusedLabelColor = Active,
                        unfocusedLabelColor = NonActive,
                        focusedTextColor = Active,
                        unfocusedTextColor = NonActive

                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    ),
                    value = deviceItem.deviceAddress.toString(),
                    label = {
                        Text(
                            text = stringResource(R.string.port), color = Active
                        )
                    },
                    onValueChange = {
                        settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                            deviceAddress = it.toInt()
                        ))
                    })
            }
        }

    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .drawBehind {
                //need invert coordinates for rotate
                drawLine(
                    brush = Brush.horizontalGradient(colors = BorderColorOneWay),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = BorderSize.toPx(),
                )
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.type_of_widget),
                    style = TextStyle(
                        color = White, fontSize = 25.sp
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                deviceTypeGroup().forEachIndexed { index, label ->
                    Column(
                        modifier = Modifier.wrapContentHeight().fillMaxSize(0.3f)
                            .selectable(
                                selected = (deviceItem.deviceType.toInt() == index),
                                onClick = {
                                    settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                                        deviceType = index.toString()
                                    ))
                                    },
                                role = Role.RadioButton
                            ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                modifier = Modifier.padding(end = 10.dp),
                                selected = (deviceItem.deviceType.toInt() == index),
                                onClick = null, // null recommended for accessibility with screen readers
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Active,
                                    unselectedColor = NonActive
                                ),
                            )
                            Text(text = label, color = White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }

    }
    Row(modifier = Modifier.fillMaxWidth().drawBehind {
        //need invert coordinates for rotate
        drawLine(
            brush = Brush.horizontalGradient(colors = BorderColorOneWay),
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = BorderSize.toPx(),
        )

    }, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.device_data_parameter),
                    style = TextStyle(
                        color = White, fontSize = 25.sp
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(0.3f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Active,
                        unfocusedBorderColor = NonActive,
                        focusedLabelColor = Active,
                        unfocusedLabelColor = NonActive,
                        focusedTextColor = Active,
                        unfocusedTextColor = NonActive

                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    ),
                    value = deviceItem.deviceDataMin.toString(),
                    label = {
                        Text(
                            text = stringResource(R.string.min_value),
                            color = Active
                        )
                    },
                    onValueChange = {
                        settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                            deviceDataMin = it.toDouble()
                        ))
                    })
                OutlinedTextField(modifier = Modifier.fillMaxWidth(0.3f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Active,
                        unfocusedBorderColor = NonActive,
                        focusedLabelColor = Active,
                        unfocusedLabelColor = NonActive,
                        focusedTextColor = Active,
                        unfocusedTextColor = NonActive

                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    ),
                    value = deviceItem.deviceDataMax.toString(),
                    label = {
                        Text(
                            text = stringResource(R.string.max_value),
                            color = Active
                        )
                    },
                    onValueChange = {
                        settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                            deviceDataMax = it.toDouble()
                        ))
                    })

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Active,
                        unfocusedBorderColor = NonActive,
                        focusedLabelColor = Active,
                        unfocusedLabelColor = NonActive,
                        focusedTextColor = Active,
                        unfocusedTextColor = NonActive

                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    ),
                    value = deviceItem.deviceDataUnits,
                    label = { Text(text = "Value", color = Active) },
                    onValueChange = {
                        settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                            deviceDataUnits = it
                        ))
                    },
                )
            }
        }

    }

    Row(modifier = Modifier.fillMaxWidth().drawBehind {
        //need invert coordinates for rotate
        drawLine(
            brush = Brush.horizontalGradient(colors = BorderColorOneWay),
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = BorderSize.toPx(),
        )

    }.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    text = stringResource(R.string.device_data_parameter),
                    style = TextStyle(
                        color = White, fontSize = 25.sp
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {


                Row(
                    modifier = Modifier.width(100.dp).height(100.dp).padding(10.dp)
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(color = Color(deviceItem.deviceColorIcon))
                        .clickable(onClick = { isDeviceIconVisible = true })
                ) {
                    Button(onClick = { isDeviceIconVisible = true }) {
                        Text(text = stringResource(R.string.choice_icon))
                    }
                }

                Row(
                    modifier = Modifier.width(100.dp).height(100.dp).padding(10.dp)
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(color = Color(deviceItem.deviceColorIcon))
                        .clickable(onClick = { isDeviceColorFirstVisible = true })
                ) {
                    Button(onClick = { isDeviceColorFirstVisible = true }) {
                        Text(text = stringResource(R.string.choice_icon))
                    }
                }

                if (isDeviceColorFirstVisible) {
                    Popup(alignment = Alignment.TopStart, onDismissRequest = {
                        isDeviceColorFirstVisible = false
                    }, properties = PopupProperties(
                        dismissOnClickOutside = false
                    ), content = {
                        ColorPicker(
                            colors = userColors,
                            selectedColor = Color(deviceItem.deviceColorIcon),
                            onColorSelected = { color ->
                                if (color != null) {
                                    settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                                        deviceColorIcon = color.toArgb()
                                    ))
                                }
                            },
                            onDismissRequest = {
                                isDeviceColorFirstVisible = false
                            })

                    })


                    /*
                                            if (isDeviceColorSecondVisible) {
                                                AlertDialog(onDismissRequest = {
                                                    isDeviceColorSecondVisible = false
                                                }, title = {}, text = {
                                                    SingleColorPickerModalExample(colors = userColors,
                                                        initialSelectedColor = deviceColorIndicator,
                                                        onColorSelected = { color ->
                                                            if (color != null) {
                                                                deviceColorIndicator = color
                                                            }
                                                        },
                                                        onDismissRequest = {
                                                            isDeviceColorSecondVisible = false
                                                        })
                                                }, buttons = {})
                                            }
                                            Row(
                                                modifier = Modifier.width(100.dp).height(100.dp).padding(10.dp)
                                                    .clip(shape = RoundedCornerShape(size = 12.dp))
                                                    .background(color = deviceColorIndicator)
                                                    .clickable(onClick = { isDeviceColorSecondVisible = true })
                                            ) {

                                            }

                     */

                    Checkbox(
                        checked = deviceItem.deviceTopBar,
                        onCheckedChange = {
                            settingViewModel.updateSingleDevice(settingViewModel.uiState.copy(
                                deviceTopBar = it
                            ))
                           }
                    )


                }
            }


        }
    }

}

@Composable
fun Preview(
    navController: NavHostController,
    mDeviceViewModel: DeviceViewModel
) {
    val settingViewModel: SettingViewModel = viewModel()
   // val deviceItem by settingViewModel.uiState.collectAsState()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            text = stringResource(R.string.preview),
            style = TextStyle(
                color = White, fontSize = 25.sp
            )
        )

    }
    Row(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        when (settingViewModel.uiState.deviceType) {
            "0" -> card(
                settingViewModel.uiState.deviceName,
                settingViewModel.uiState.deviceData,
                settingViewModel.uiState.deviceDataMin,
                settingViewModel.uiState.deviceDataMax,
                settingViewModel.uiState.deviceDataUnits,
                Color(settingViewModel.uiState.deviceColorIcon),
                Color(settingViewModel.uiState.deviceColorIndicator),
                settingViewModel.uiState.deviceIcon,
                settingViewModel.uiState.deviceType.toInt(),
            )

            "1" -> widget(
                settingViewModel.uiState.deviceName,
                settingViewModel.uiState.deviceData,
                settingViewModel.uiState.deviceDataMin,
                settingViewModel.uiState.deviceDataMax,
                settingViewModel.uiState.deviceDataUnits,
                Color(settingViewModel.uiState.deviceColorIcon),
                Color(settingViewModel.uiState.deviceColorIndicator),
                settingViewModel.uiState.deviceIcon,
                settingViewModel.uiState.deviceType.toInt(),
            )

            "2" -> widget(
                settingViewModel.uiState.deviceName,
                settingViewModel.uiState.deviceData,
                settingViewModel.uiState.deviceDataMin,
                settingViewModel.uiState.deviceDataMax,
                settingViewModel.uiState.deviceDataUnits,
                Color(settingViewModel.uiState.deviceColorIcon),
                Color(settingViewModel.uiState.deviceColorIndicator),
                settingViewModel.uiState.deviceIcon,
                settingViewModel.uiState.deviceType.toInt(),
            )
        }
    }


    val deviceItems = DeviceItem(
        deviceId=settingViewModel.uiState.deviceId,
        deviceGateAddress=settingViewModel.uiState.deviceGateAddress,
        deviceAddress=settingViewModel.uiState.deviceAddress,
        deviceName=settingViewModel.uiState.deviceName,
        deviceType=settingViewModel.uiState.deviceType,
        deviceData=settingViewModel.uiState.deviceData,
        deviceDataMax=settingViewModel.uiState.deviceDataMax,
        deviceDataMin=settingViewModel.uiState.deviceDataMin,
        deviceDataUnits=settingViewModel.uiState.deviceDataUnits,
        deviceColorIcon=settingViewModel.uiState.deviceColorIcon,
        deviceColorIndicator=settingViewModel.uiState.deviceColorIndicator,
        deviceColorAlarm=settingViewModel.uiState.deviceColorAlarm,
        deviceIcon=settingViewModel.uiState.deviceIcon,
        deviceTopBar=settingViewModel.uiState.deviceTopBar
    )
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedButton(onClick = {
            mDeviceViewModel.insert(deviceItems)
            navController.navigate(SettingsDevices.route)
        }, modifier = Modifier.padding(10.dp)) {
            Text(text = stringResource(R.string.Save), color = White)
        }
        OutlinedButton(onClick = {
            mDeviceViewModel.deleteDevice(deviceItems)
            navController.navigate(SettingsDevices.route)
        }, modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(R.string.delete_device),
                style = MaterialTheme.typography.labelMedium
            )
        }

    }





}