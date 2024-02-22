package com.mo.app.carcontroller.page.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mo.app.carcontroller.data.DeviceInfo
import com.mo.app.carcontroller.page.device.DeviceIntent
import com.mo.app.carcontroller.page.device.DeviceUiState
import com.mo.app.carcontroller.util.PicoNetwork
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ControllerViewModel : ViewModel() {
    //Channel信道，意图发送别ViewModel，
    val controllerChannel = Channel<ControllerIntent>(Channel.UNLIMITED)
    //状态管理
    var uiState by mutableStateOf(ControllerUiState())

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            controllerChannel.consumeAsFlow().collect() {
                when (it) {
                    is ControllerIntent.Connect -> connect()
                    is ControllerIntent.Forward -> forward()
                    is ControllerIntent.Backward -> backward()
                    is ControllerIntent.Left -> left()
                    is ControllerIntent.Right -> right()
                    is ControllerIntent.Stop -> stop()
                    is ControllerIntent.Center -> center()
                }
            }
        }
    }

    private fun connect() {
        viewModelScope.launch {
            Timber.d("尝试连接到设备  ${uiState.deviceInfo.ip}:${uiState.deviceInfo.port}")
            uiState = uiState.copy(picoNetwork = PicoNetwork(uiState.deviceInfo))
        }
    }
    private fun forward() {
        viewModelScope.launch {
            Timber.d("执行前进")
            uiState.picoNetwork?.forward()
        }
    }
    private fun backward() {
        viewModelScope.launch {
            Timber.d("执行后退")
            uiState.picoNetwork?.backward()
        }
    }
    private fun left() {
        viewModelScope.launch {
            Timber.d("执行左转")
            uiState.picoNetwork?.left()
        }
    }
    private fun right() {
        viewModelScope.launch {
            Timber.d("执行右转")
            uiState.picoNetwork?.right()
        }
    }
    private fun stop() {
        viewModelScope.launch {
            Timber.d("执行停止")
            uiState.picoNetwork?.stop()
        }
    }
    private fun center() {
        viewModelScope.launch {
            Timber.d("执行归中")
            uiState.picoNetwork?.center()
        }
    }
}