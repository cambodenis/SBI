package com.example.sbi.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeviceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DeviceRepository

    val readAllData: LiveData<List<DeviceItem>>

    init {
        val deviceDao = DeviceDatabase.getInstance(application).deviceDao()
        repository = DeviceRepository(deviceDao)
        readAllData = repository.readAllData
    }

    fun getListDevice(deviceType: String): LiveData<List<DeviceItem>> {
        return repository.getListDevice(deviceType)
    }

    fun getDeviceById(id: Int): LiveData<List<DeviceItem>> {
        return repository.getDeviceById(id)
    }

    fun getDeviceTopBarIcon(topBar: Boolean): LiveData<List<DeviceItem>> {
        return repository.getTopBarIcon(topBar)
    }

    fun addDevice(deviceItem: DeviceItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDevice(deviceItem)
        }
    }

    fun updateDevice(deviceItem: DeviceItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDevice(deviceItem = deviceItem)
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