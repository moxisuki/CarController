package com.mo.app.carcontroller.page.controller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mo.app.carcontroller.page.device.DeviceViewModel
import com.mo.app.carcontroller.util.PicoNetwork
import timber.log.Timber

@Composable
fun ControllerPage(navController: NavHostController, viewModel: ControllerViewModel) {
    val scrollState = rememberLazyListState()
    var isStarted by remember { mutableStateOf(false) }

    var left by remember { mutableStateOf(false) }
    var right by remember { mutableStateOf(false) }
    var up by remember { mutableStateOf(false) }
    var down by remember { mutableStateOf(false) }
    var stop by remember { mutableStateOf(false) }
    var center by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiState.logs.size) {
        scrollState.animateScrollToItem(viewModel.uiState.logs.size)
    }
    LaunchedEffect(isStarted) {
        if (isStarted) {
            viewModel.controllerChannel.send(ControllerIntent.Connect)
            isStarted = false
        }
    }
    LaunchedEffect(left) {
        if (left) {
            viewModel.controllerChannel.send(ControllerIntent.Left)
            left = false
        }
    }
    LaunchedEffect(right) {
        if (right) {
            viewModel.controllerChannel.send(ControllerIntent.Right)
            right = false
        }
    }
    LaunchedEffect(up) {
        if (up) {
            viewModel.controllerChannel.send(ControllerIntent.Forward)
            up = false
        }
    }
    LaunchedEffect(down) {
        if (down) {
            viewModel.controllerChannel.send(ControllerIntent.Backward)
            down = false
        }
    }
    LaunchedEffect(stop) {
        if (stop) {
            viewModel.controllerChannel.send(ControllerIntent.Stop)
            stop = false
        }
    }
    LaunchedEffect(center) {
        if (center) {
            viewModel.controllerChannel.send(ControllerIntent.Center)
            center = false
        }
    }


    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxSize()
                .weight(0.1f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .padding(20.dp)
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            // end
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "start",
                modifier = Modifier
                    .padding(start = 270.dp, top = 20.dp, bottom = 20.dp)
                    .size(30.dp)
                    .clickable {
                        isStarted = true
                    }
            )
        }
        // Console
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp)
                .weight(0.4f)
        ) {
            LazyColumn(state = scrollState) {
                items(viewModel.uiState.logs) { log ->
                    Text(text = log)
                }
            }

        }
        Column(
            Modifier
                .fillMaxSize()
                .weight(0.5f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                ) {
                    // 方向按键
                    Row(
                        Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "left",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    left = true
                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "right",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    right = true
                                }
                        )

                    }
                    // 方向按键 up down
                    Column(
                        Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "up",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    up = true
                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "down",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    down = true
                                }
                        )

                    }
                    Icon(Icons.Filled.Refresh, contentDescription = "center", modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            center = true
                        })

                    Icon(Icons.Filled.Close, contentDescription = "stop", modifier = Modifier
                        .padding(top = 300.dp, start = 250.dp)
                        .size(50.dp)
                        .clickable {
                            stop = true
                        })
                }

            }

        }

    }
}

@Composable
fun LogWindow(logs: List<String>) {

}

@Preview
@Composable
fun ControllerPagePreview() {

    val viewModel = ControllerViewModel()
    val navController = rememberNavController()
    ControllerPage(navController, viewModel)
}