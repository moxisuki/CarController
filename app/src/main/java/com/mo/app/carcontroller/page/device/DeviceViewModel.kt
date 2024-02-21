package com.mo.app.carcontroller.page.device

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class DeviceViewModel:ViewModel() {
    //Channel信道，意图发送别ViewModel，
    val deviceChannel = Channel<DeviceIntent>(Channel.UNLIMITED)

    //状态管理
    var uiState by mutableStateOf(DeviceUiState())

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            deviceChannel.consumeAsFlow().collect() {
                when (it) {
                    is DeviceIntent.LoadDevices -> loadDevices()
                    is DeviceIntent.AddDevice -> addDevice(it.ip, it.port)
                    is DeviceIntent.DeleteDevice -> deleteDevice(it.ip)
                }
            }
        }
    }

    private fun addDevice(ip: String, port: Int) {
        viewModelScope.launch {
            val list = uiState.devicesList.toMutableMap()
            list.put(ip, port)
            uiState = uiState.copy(devicesList = list)
        }
    }
    private fun deleteDevice(ip: String) {
        viewModelScope.launch {
            val list = uiState.devicesList.toMutableMap()
            list.remove(ip)
            uiState = uiState.copy(devicesList = list)
        }
    }

    private val devicesFlow: Flow<Map<String,Int>> = flow {
        val list = mutableMapOf<String,Int>()
        list.put("192.168.31.65",6666)
        list.put("192.168.31.23",7777)
        emit(list)
    }
    private fun loadDevices() {
    viewModelScope.launch {
        devicesFlow.flowOn(Dispatchers.Default).collect { contents ->
            uiState = uiState.copy(devicesList = contents)
        }
        }
    }
}
