package com.example.sbi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeviceDatabaseDao {
    @Query("SELECT * from device_list")
    fun getAll(): LiveData<List<DeviceItem>>

    @Query("SELECT * FROM device_list WHERE device_type = :deviceType")
    fun getListDevice(deviceType: String): LiveData<List<DeviceItem>>

    @Query("SELECT * FROM device_list WHERE deviceTopBar = :deviceTopBar")
    fun getTopBarIcon(deviceTopBar: Boolean): LiveData<List<DeviceItem>>


    @Query("SELECT * FROM device_list WHERE deviceId = :id")
    fun getDeviceById(id: Int): LiveData<List<DeviceItem>>

    @Insert
    suspend fun insert(item: DeviceItem)

    @Update
    suspend fun update(item: DeviceItem)

    @Delete
    suspend fun delete(item: DeviceItem)

    @Query("DELETE FROM device_list")
    suspend fun deleteAllDevices()
}