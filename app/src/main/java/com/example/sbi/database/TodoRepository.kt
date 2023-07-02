package com.example.sbi.database

import androidx.lifecycle.LiveData

class DeviceRepository(private val deviceDatabaseDao: DeviceDatabaseDao) {

    val readAllData: LiveData<List<DeviceItem>> = deviceDatabaseDao.getAll()

    fun getListDevice(deviceType: String): LiveData<List<DeviceItem>> =
        deviceDatabaseDao.getListDevice(deviceType)

    fun getTopBarIcon(deviceTopBarIcon: Boolean): LiveData<List<DeviceItem>> =
        deviceDatabaseDao.getTopBarIcon(deviceTopBarIcon)

    fun getDeviceById(id: Int): LiveData<List<DeviceItem>> = deviceDatabaseDao.getDeviceById(id)

    suspend fun addDevice(deviceItem: DeviceItem) {
        deviceDatabaseDao.insert(deviceItem)
    }

    suspend fun updateDevice(deviceItem: DeviceItem) {
        deviceDatabaseDao.update(deviceItem)
    }

    suspend fun deleteDevice(deviceItem: DeviceItem) {
        deviceDatabaseDao.delete(deviceItem)
    }

    suspend fun deleteAllDevices() {
        deviceDatabaseDao.deleteAllDevices()
    }
}