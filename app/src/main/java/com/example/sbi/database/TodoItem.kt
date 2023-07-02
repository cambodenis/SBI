package com.example.sbi.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_list")
data class DeviceItem(

    @PrimaryKey(autoGenerate = true)
    var deviceId: Int?,

    @ColumnInfo(name = "device_gate_address")
    val deviceGateAddress: Double,

    @ColumnInfo(name = "device_address")
    val deviceAddress: Double,

    @ColumnInfo(name = "device_name")
    val deviceName: String,

    //Card, Button, Widget

    @ColumnInfo(name = "device_type")
    val deviceType: String,

    @ColumnInfo(name = "device_data")
    val deviceData: Double,

    @ColumnInfo(name = "device_data_max")
    val deviceDataMax: Double,

    @ColumnInfo(name = "device_data_min")
    val deviceDataMin: Double,

    @ColumnInfo(name = "device_data_units")
    val deviceDataUnits: String,

    @ColumnInfo(name = "device_color_icon")
    val deviceColorIcon: Int,

    @ColumnInfo(name = "device_color_indicator")
    val deviceColorIndicator: Int,

    @ColumnInfo(name = "device_color_alarm")
    val deviceColorAlarm: Int,

    @ColumnInfo(name = "device_icon")
    var deviceIcon: String,

    @ColumnInfo(name = "deviceTopBar")
    val deviceTopBar: Boolean
)
