package com.example.sbi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDatabaseDao {
    @Query("SELECT * from device_list")
    fun getAll(): Flow<List<DeviceItem>>

    @Query("SELECT * FROM device_list WHERE device_type = :deviceType")
    fun getListDevice(deviceType: String): Flow<List<DeviceItem>>

    @Query("SELECT * FROM device_list WHERE deviceTopBar = :deviceTopBar")
    fun getTopBarIcon(deviceTopBar: Boolean): Flow<List<DeviceItem>>


    @Query("SELECT * FROM device_list WHERE deviceId == :id")
    suspend fun getDeviceById(id: Int): DeviceItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DeviceItem)

    @Delete
    suspend fun delete(item: DeviceItem)

    @Query("DELETE FROM device_list")
    suspend fun deleteAllDevices()
}