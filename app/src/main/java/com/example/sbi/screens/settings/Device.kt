package com.example.sbi.screens.settings

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.sbi.SBI.Companion.windowWidth
import com.example.sbi.database.DeviceItem
import com.example.sbi.database.DeviceViewModel
import com.example.sbi.database.DeviceViewModelFactory
import com.example.sbi.datastore.DataStoreManager
import com.example.sbi.datastore.objectDataStore
import com.example.sbi.navigation.SettingsDevices
import com.example.sbi.ui.theme.Active
import com.example.sbi.ui.theme.BorderColorOneWay
import com.example.sbi.ui.theme.BorderColorTwoWay
import com.example.sbi.ui.theme.BorderSize
import com.example.sbi.ui.theme.NonActive
import com.example.sbi.ui.theme.Orange
import com.example.sbi.ui.theme.Red
import com.example.sbi.ui.theme.White
import com.example.sbi.ui.theme.userColors
import com.example.sbi.utils.ColorPicker
import com.example.sbi.utils.ImagePickUp
import com.example.sbi.utils.card
import com.example.sbi.utils.deviceTypeGroup
import com.example.sbi.utils.getIconList
import com.example.sbi.utils.widget


@Composable
fun Settings_Device(navController: NavHostController, id: String) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(context)
    val mDeviceViewModel: DeviceViewModel =
        viewModel(factory = DeviceViewModelFactory(context.applicationContext as Application))


    val name = stringResource(R.string.device_name)
    val unit = stringResource(R.string.device_units)


    val addDevice = DeviceItem(
        null,
        deviceGateAddress = 0.0,
        deviceAddress = 0.0,
        deviceName = name,
        deviceType = "0",
        deviceData = 0.0,
        deviceDataMax = 0.0,
        deviceDataMin = 0.0,
        deviceDataUnits = unit,
        deviceColorIcon = White.toArgb(),
        deviceColorIndicator = Orange.toArgb(),
        deviceColorAlarm = Red.toArgb(),
        deviceIcon = "File:///assets",
        deviceTopBar = false
    )


    val btn1_state = objectDataStore(context, "buttonState").getObjectState("btn_1")
    val btn2_state = objectDataStore(context, "buttonState").getObjectState("btn_2")
    val btn1_state_mut = remember {
        mutableStateOf(objectDataStore(context, "buttonState").getObjectState("btn_1"))
    }
    val btn2_state_mut = remember {
        mutableStateOf(objectDataStore(context, "buttonState").getObjectState("btn_2"))
    }

    val bgColorState = remember {
        mutableStateOf(White.value)
    }
    val textSizeState = remember {
        mutableStateOf(40)
    }
    val buttonState_1 = remember {
        mutableStateOf(false)
    }

    val buttonState_2 = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        dataStoreManager.getSettings("buttonState_1").collect { settings ->
            bgColorState.value = settings.bgColor.toULong()
            textSizeState.value = settings.textSize
            buttonState_1.value = settings.buttonState
        }


    }
    LaunchedEffect(key1 = true) {
        dataStoreManager.getSettings("buttonState_2").collect { settings ->
            bgColorState.value = settings.bgColor.toULong()
            textSizeState.value = settings.textSize
            buttonState_2.value = settings.buttonState

        }
    }
    if (id.toInt() >= 0) {
        val deviceItem = mDeviceViewModel.getDeviceById(id.toInt()).observeAsState(listOf()).value
        deviceItem.forEach { item ->
            var updateDevice = DeviceItem(
                deviceId = item.deviceId!!.toInt(),
                deviceGateAddress = item.deviceGateAddress,
                deviceAddress = item.deviceAddress,
                deviceName = item.deviceName,
                deviceType = item.deviceType,
                deviceData = 65.00,
                deviceDataMax = item.deviceDataMax,
                deviceDataMin = item.deviceDataMin,
                deviceDataUnits = item.deviceDataUnits,
                deviceColorIcon = item.deviceColorIcon,
                deviceColorIndicator = item.deviceColorIndicator,
                deviceColorAlarm = item.deviceColorAlarm,
                deviceIcon = item.deviceIcon,
                deviceTopBar = item.deviceTopBar
            )
            deviceItem(updateDevice, navController)
        }
    } else deviceItem(addDevice, navController)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun deviceItem(deviceItem: (DeviceItem), navController: NavHostController) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(context)
    val mDeviceViewModel: DeviceViewModel =
        viewModel(factory = DeviceViewModelFactory(context.applicationContext as Application))

    val deviceId = deviceItem.deviceId

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

    val device = DeviceItem(
        deviceId,
        deviceGateAddress,
        deviceAddress,
        deviceName,
        deviceType.toString(),
        deviceData,
        deviceDataMax,
        deviceDataMin,
        deviceDataUnits,
        deviceColorIcon.toArgb(),
        deviceColorIndicator.toArgb(),
        deviceColorAlarm.toArgb(),
        deviceIcon,
        deviceTopBar
    )
    Row(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())

    ) {

        var isDeviceIconVisible by remember { mutableStateOf(false) }
        var isDeviceColorFirstVisible by remember { mutableStateOf(false) }
        var isDeviceColorSecondVisible by remember { mutableStateOf(false) }
        if (isDeviceIconVisible) {
            Popup(alignment = Alignment.TopStart, onDismissRequest = {
                isDeviceIconVisible = false
            }, properties = PopupProperties(
                dismissOnClickOutside = false
            ), content = {

                ImagePickUp(
                    getIconList("icon")!!,
                    onIconSelected = { selectedIcon ->
                        deviceItem.deviceIcon = selectedIcon
                    }) {
                    isDeviceIconVisible = false
                }
            })

        }
        Column(
            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight()
                .drawBehind {
                    rotate(180f) {
                        drawLine(
                            brush = Brush.verticalGradient(colors = BorderColorTwoWay),
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = BorderSize.toPx(),
                        )
                    }
                }) {


            /*
            Button(
                onClick = {
                    coroutine.launch {
                        objectDataStore(context, "buttonState").setObjectState("btn_1" , state = true)

                    }
                }
            ) {

                Text(text= btn1_state_mut.toString() ,style = TextStyle(
                    color = Color(bgColorState.value), fontSize = 25.sp
                ) )}

            Button(
                onClick = {
                    coroutine.launch {
                        objectDataStore(context, "buttonState").setObjectState("btn_1" , state = false)

                        /*
                        dataStoreManager.saveSettings(
                            SettingsData(
                                10,
                                Main.value.toLong(),
                                true
                            ), "buttonState_2"
                        )

                         */
                    }
                }
            ) {

                Text(text= btn2_state_mut.toString() ,style = TextStyle(
                    color = Color(bgColorState.value), fontSize = 25.sp
                ) )}

             */

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
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = deviceId.toString(),
                        style = TextStyle(
                            color = White, fontSize = 25.sp
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f),
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
                            value = deviceName,
                            label = {
                                Text(
                                    text = stringResource(R.string.device_name),
                                    color = Active
                                )
                            },
                            onValueChange = { deviceName = it })
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
                            value = deviceGateAddress.toString(),
                            label = {
                                Text(
                                    text = stringResource(R.string.gate), color = Active
                                )
                            },
                            onValueChange = {
                                deviceGateAddress = it.toDouble()
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
                            value = deviceAddress.toString(),
                            label = {
                                Text(
                                    text = stringResource(R.string.port), color = Active
                                )
                            },
                            onValueChange = {
                                deviceAddress = it.toDouble()
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
                                modifier = Modifier.wrapContentHeight().width(200.dp)
                                    .selectable(
                                        selected = (deviceType == index),
                                        onClick = { deviceType = index },
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
                                        selected = (deviceType == index),
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
                            value = deviceDataMin.toString(),
                            label = {
                                Text(
                                    text = stringResource(R.string.min_value),
                                    color = Active
                                )
                            },
                            onValueChange = {
                                deviceDataMin = it.toDouble()
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
                            value = deviceDataMax.toString(),
                            label = {
                                Text(
                                    text = stringResource(R.string.max_value),
                                    color = Active
                                )
                            },
                            onValueChange = {
                                deviceDataMax = it.toDouble()
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
                            value = deviceDataUnits,
                            label = { Text(text = "Value", color = Active) },
                            onValueChange = {
                                deviceDataUnits = it
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
                                .background(color = deviceColorIcon)
                                .clickable(onClick = { isDeviceIconVisible = true })
                        ) {
                            Button(onClick = { isDeviceIconVisible = true }) {
                                Text(text = stringResource(R.string.choice_icon))
                            }
                        }

                        Row(
                            modifier = Modifier.width(100.dp).height(100.dp).padding(10.dp)
                                .clip(shape = RoundedCornerShape(size = 12.dp))
                                .background(color = deviceColorIcon)
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
                                    selectedColor = deviceColorIcon,
                                    onColorSelected = { color ->
                                        if (color != null) {
                                            deviceColorIcon = color
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
                                checked = deviceTopBar,
                                onCheckedChange = { deviceTopBar = it }
                            )


                        }
                    }


                }
            }


            Column(
                modifier = Modifier.fillMaxWidth(windowWidth / 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                when (deviceType) {
                    0 -> card(
                        deviceName,
                        deviceData.toDouble(),
                        deviceDataMin.toDouble(),
                        deviceDataMax.toDouble(),
                        deviceDataUnits,
                        deviceColorIcon,
                        deviceColorIndicator,
                        deviceIcon,
                        deviceType,
                    )

                    1 -> widget(
                        deviceName,
                        deviceData.toDouble(),
                        deviceDataMin.toDouble(),
                        deviceDataMax.toDouble(),
                        deviceDataUnits,
                        deviceColorIcon,
                        deviceColorIndicator,
                        deviceIcon,
                        deviceType,
                    )

                    2 -> widget(
                        deviceName,
                        deviceData.toDouble(),
                        deviceDataMin.toDouble(),
                        deviceDataMax.toDouble(),
                        deviceDataUnits,
                        deviceColorIcon,
                        deviceColorIndicator,
                        deviceIcon,
                        deviceType,
                    )
                }


                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().height(60.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Button(modifier = Modifier.clip(CircleShape),
                                onClick = {
                                if (deviceId != null) mDeviceViewModel.updateDevice(device) else mDeviceViewModel.addDevice(device)
                                navController.navigate(SettingsDevices.route)
                            }) {
                                Text(stringResource(R.string.save))
                            }
                            Button(onClick = {
                                mDeviceViewModel.deleteDevice(device)
                                navController.navigate(SettingsDevices.route)
                            }) {
                                Text(stringResource(R.string.delete_device))
                            }
                        }
                    }

                }
            }
        }
    }
}

