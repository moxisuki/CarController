package com.mo.app.carcontroller.util

import com.mo.app.carcontroller.data.DeviceInfo
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.InetSocketAddress
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.NoRouteToHostException
import java.nio.charset.Charset

class PicoNetwork(deviceInfo: DeviceInfo){
    lateinit var outputChannel: ByteWriteChannel
    init {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
                    .connect(InetSocketAddress(deviceInfo.ip, deviceInfo.port))
                val receive = socket.openReadChannel()
                val output = socket.openWriteChannel(autoFlush = true)
                outputChannel = output
                output.writeStringUtf8("""{"action":"connect","value":0}""")
                receive.read {
                    val charset = Charset.forName("UTF-8")
                    val message = charset.decode(it).toString()
                    Timber.d("收到消息: $message")
                }


            } catch (e: Exception) {
                when (e) {
                    is NoRouteToHostException -> {
                        Timber.e("找不到目标主机")
                    }
                }
                e.printStackTrace()
                Timber.e(e)
            }
        }

    }
    fun forward() {
        CoroutineScope(Dispatchers.Default).launch {
            outputChannel.writeStringUtf8("""{"action":"forward","value":0}""")
        }
    }
    fun backward() {
        CoroutineScope(Dispatchers.Default).launch {
            outputChannel.writeStringUtf8("""{"action":"backward","value":0}""")
        }
    }
    fun left() {
        CoroutineScope(Dispatchers.Default).launch {
            outputChannel.writeStringUtf8("""{"action":"left","value":0}""")
        }
    }
    fun right() {
        CoroutineScope(Dispatchers.Default).launch {
            outputChannel.writeStringUtf8("""{"action":"right","value":0}""")
        }
    }
    fun stop() {
        CoroutineScope(Dispatchers.Default).launch {
            outputChannel.writeStringUtf8("""{"action":"stop","value":0}""")
        }
    }
    fun center() {
        CoroutineScope(Dispatchers.Default).launch {
            outputChannel.writeStringUtf8("""{"action":"center","value":0}""")
        }
    }
}