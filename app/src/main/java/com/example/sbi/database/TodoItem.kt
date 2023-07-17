package com.example.sbi.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_list")
data class DeviceItem(

    @PrimaryKey(autoGenerate = true)
    var deviceId: Int?,

    @ColumnInfo(name = "device_gate_address")
    var deviceGateAddress: Int,

    @ColumnInfo(name = "device_address")
    var deviceAddress: Int,

    @ColumnInfo(name = "device_name")
    var deviceName: String,

    //Card, Button, Widget

    @ColumnInfo(name = "device_type")
    var deviceType: String,

    @ColumnInfo(name = "device_data")
    val deviceData: Double,

    @ColumnInfo(name = "device_data_max")
    var deviceDataMax: Double,

    @ColumnInfo(name = "device_data_min")
    var deviceDataMin: Double,

    @ColumnInfo(name = "device_data_units")
    var deviceDataUnits: String,

    @ColumnInfo(name = "device_color_icon")
    var deviceColorIcon: Int,

    @ColumnInfo(name = "device_color_indicator")
    val deviceColorIndicator: Int,

    @ColumnInfo(name = "device_color_alarm")
    val deviceColorAlarm: Int,

    @ColumnInfo(name = "device_icon")
    var deviceIcon: String,

    @ColumnInfo(name = "deviceTopBar")
    var deviceTopBar: Boolean
)
