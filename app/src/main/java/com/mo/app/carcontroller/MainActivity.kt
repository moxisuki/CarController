package com.mo.app.carcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mo.app.carcontroller.page.controller.ControllerPage
import com.mo.app.carcontroller.page.device.DeviceViewModel
import com.mo.app.carcontroller.page.device.DevicesPage
import com.mo.app.carcontroller.ui.theme.CarControllerTheme
import timber.log.Timber


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<DeviceViewModel>()
    private val viewModel2 by viewModels<DeviceViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Timber
        Timber.plant(Timber.DebugTree())

        setContent {
            CarControllerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "devicesPage") {
                        composable("devicesPage") { DevicesPage(navController,viewModel) }
                        composable("controllerPage") { ControllerPage(navController,viewModel2) }
                    }
                }
            }
        }
    }
}