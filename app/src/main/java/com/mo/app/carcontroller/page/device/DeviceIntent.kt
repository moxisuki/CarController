package com.mo.app.carcontroller.page.device

sealed class DeviceIntent {
    data object LoadDevices : DeviceIntent()
    class AddDevice(val ip: String, val port: Int) : DeviceIntent()
    class DeleteDevice(val ip: String) : DeviceIntent()
}