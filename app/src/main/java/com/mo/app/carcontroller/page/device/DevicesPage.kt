package com.mo.app.carcontroller.page.device

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mo.app.carcontroller.data.DeviceInfo
import timber.log.Timber

@Composable
fun DevicesPage(navHostController: NavHostController, viewModel: DeviceViewModel,callback:(DeviceInfo)->Unit){

    val viewState = viewModel.uiState

    var addDevice by remember { mutableStateOf(false) }
    var deleteDevice by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    var ip by remember { mutableStateOf(TextFieldValue()) }
    var port by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(key1 = true) {
        viewModel.deviceChannel.send(DeviceIntent.LoadDevices)
    }
    LaunchedEffect(deleteDevice) {
        if (deleteDevice) {
            Timber.d("deleteDevice,${ip.text}")
            viewModel.deviceChannel.send(DeviceIntent.DeleteDevice(ip.text))
            deleteDevice = false
        }
    }
    LaunchedEffect(addDevice) {
        if (addDevice) {
            viewModel.deviceChannel.send(DeviceIntent.AddDevice(ip.text,port.text.toInt()))
            addDevice = false
        }
    }
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "DEVICES",
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxSize()
                    .padding(top = 40.dp),
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(35.dp)
            ) {
                Text(
                    text = "Select A Device",
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                Icon(Icons.Filled.Add, contentDescription = "ADD",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .clickable {
                            showDialog.value = true
                        }
                )
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(0.8f)
            ) {

                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    viewState.devicesList.forEach {
                        item {
                            DevicesPageList(ip = it.key, port = it.value.toString()){
                                action,ip2,port2->
                                ip = TextFieldValue(ip2)
                                port = TextFieldValue(port2)
                                when(action){
                                    "edit"->{
                                        deleteDevice = true
                                        showDialog.value = true
                                    }
                                    "delete"->{
                                        deleteDevice = true
                                    }
                                    "connect"->{
                                        navHostController.navigate("controllerPage")
                                        callback(DeviceInfo(ip2,port2.toInt()))
                                    }
                                }
                            }
                        }
                    }
                }
                Icon(Icons.Filled.Settings, contentDescription = "Settings",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .clickable {

                        }
                )
            }
        }
        if (showDialog.value) {
            // show dialog

            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Device info") },
                text = {
                    Column {
                        TextField(
                            value = ip,
                            onValueChange = { ip = it },
                            label = { Text("IP") }
                        )
                        TextField(
                            value = port,
                            onValueChange = { port = it },
                            label = { Text("Port") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            if (ip.text.isNotEmpty() && port.text.isNotEmpty()) addDevice = true
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {}
            )
        }
    }

}

@Composable
fun DevicesPageList(ip:String,port:String,operating:(action:String,ip:String,port:String)->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .clickable {
                operating("connect",ip,port)
            }
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "IP: $ip",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    textAlign = TextAlign.Start,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
                Text(
                    text = "PORT: $port",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    textAlign = TextAlign.Start,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //DELETE AND EDIT
                Icon(Icons.Filled.Edit, contentDescription = "Edit",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 3.dp)
                        .clickable {
                            operating("edit",ip,port)
                        }
                )
                Icon(Icons.Filled.Delete, contentDescription = "Delete",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            operating("delete",ip,port)
                        }
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DevicesPagePreview() {

    val viewModel = DeviceViewModel()
    val navController = rememberNavController()

    DevicesPage(navController, viewModel){}
}