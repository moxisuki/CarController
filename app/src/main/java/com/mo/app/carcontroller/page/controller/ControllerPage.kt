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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mo.app.carcontroller.page.device.DeviceViewModel

@Composable
fun ControllerPage(navController: NavHostController, viewModel2: DeviceViewModel) {
    val logs = remember{ mutableStateListOf<String>() }
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
                        logs.add("try to connect to 192.168.6.31:6666")
                    }
            )
        }
        // Console
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp)
                .weight(0.4f)) {
            LogWindow(logs)
        }
        Column(
            Modifier
                .fillMaxSize()
                .weight(0.5f)) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().weight(0.5f),
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
                                    logs.add("left")
                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "right",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    logs.add("right")
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
                                    logs.add("up")
                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "down",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    logs.add("down")
                                }
                        )
                        }
                    }

                }

            }

        }
    }

@Composable
fun LogWindow(logs: List<String>) {
    LazyColumn {
        items(logs) { log ->
            Text(text = log)
        }
    }
}

@Preview
@Composable
fun ControllerPagePreview() {

    val viewModel = DeviceViewModel()
    val navController = rememberNavController()
    ControllerPage(navController, viewModel)
}