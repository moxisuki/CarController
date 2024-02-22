package com.mo.app.carcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mo.app.carcontroller.data.ControllerLogTree
import com.mo.app.carcontroller.data.DeviceInfo
import com.mo.app.carcontroller.page.controller.ControllerPage
import com.mo.app.carcontroller.page.controller.ControllerViewModel
import com.mo.app.carcontroller.page.device.DeviceViewModel
import com.mo.app.carcontroller.page.device.DevicesPage
import com.mo.app.carcontroller.ui.theme.CarControllerTheme
import timber.log.Timber


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<DeviceViewModel>()
    private val controllerViewModel by viewModels<ControllerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Timber
        Timber.plant(ControllerLogTree(controllerViewModel))
        Timber.tag("CarController")

        setContent {
            CarControllerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "devicesPage") {
                        composable("devicesPage") {
                            DevicesPage(navController, viewModel) {
                                controllerViewModel.uiState = controllerViewModel.uiState.copy(
                                    deviceInfo = DeviceInfo(it.ip, it.port)
                                )
                                navController.navigate("controllerPage")
                            }
                        }
                        composable("controllerPage") {
                            ControllerPage(
                                navController,
                                controllerViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}