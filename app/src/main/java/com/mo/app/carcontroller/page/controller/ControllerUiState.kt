package com.mo.app.carcontroller.page.controller

import com.mo.app.carcontroller.data.DeviceInfo
import com.mo.app.carcontroller.util.PicoNetwork

data class ControllerUiState (
    val deviceInfo: DeviceInfo = DeviceInfo("192.168.1.1",4444),
    val logs: MutableList<String> = mutableListOf(),
    val picoNetwork: PicoNetwork? = null
)