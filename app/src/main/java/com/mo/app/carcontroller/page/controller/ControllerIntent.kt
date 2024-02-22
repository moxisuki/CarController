package com.mo.app.carcontroller.page.controller

import com.mo.app.carcontroller.data.DeviceInfo

sealed class ControllerIntent {
    data object Connect : ControllerIntent()
    data object Forward : ControllerIntent()
    data object Backward : ControllerIntent()
    data object Left : ControllerIntent()
    data object Right : ControllerIntent()
    data object Stop : ControllerIntent()
    data object Center :ControllerIntent()
}