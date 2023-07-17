package com.example.sbi.database

import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val deviceDatabaseDao: DeviceDatabaseDao) {

    val readAllData: Flow<List<DeviceItem>> = deviceDatabaseDao.getAll()

    fun getListDevice(deviceType: String): Flow<List<DeviceItem>> =
        deviceDatabaseDao.getListDevice(deviceType)

    fun getTopBarIcon(deviceTopBarIcon: Boolean): Flow<List<DeviceItem>> =
        deviceDatabaseDao.getTopBarIcon(deviceTopBarIcon)

    fun getDeviceById(id: Int): Flow<List<DeviceItem>> = deviceDatabaseDao.getDeviceById(id)

    suspend fun insert(deviceItem: DeviceItem) {
        deviceDatabaseDao.insert(deviceItem)
    }

    suspend fun deleteDevice(deviceItem: DeviceItem) {
        deviceDatabaseDao.delete(deviceItem)
    }

    suspend fun deleteAllDevices() {
        deviceDatabaseDao.deleteAllDevices()
    }
}