package com.example.sbi.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DeviceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DeviceRepository

    val readAllData: Flow<List<DeviceItem>>

    init {
        val deviceDao = DeviceDatabase.getInstance(application).deviceDao()
        repository = DeviceRepository(deviceDao)
        readAllData = repository.readAllData
    }

    fun getListDevice(deviceType: String): Flow<List<DeviceItem>> {
        return repository.getListDevice(deviceType)
    }

    fun getDeviceById(id: Int): Flow<List<DeviceItem>> {
        return repository.getDeviceById(id)
    }

    fun getDeviceTopBarIcon(topBar: Boolean): Flow<List<DeviceItem>> {
        return repository.getTopBarIcon(topBar)
    }

    fun insert(deviceItem: DeviceItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(deviceItem)
        }
    }

    fun deleteDevice(deviceItem: DeviceItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDevice(deviceItem = deviceItem)
        }
    }

    fun deleteAllDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDevices()
        }
    }
}

class DeviceViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DeviceViewModel::class.java)) {
            return DeviceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}