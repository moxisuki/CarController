package com.mo.app.carcontroller.page.device

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mo.app.carcontroller.R

@Composable
fun DevicesPage() {
    var isSearching by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()) {
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
                text = "Searching for devices...",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            if (isSearching) {
                CircularProgressIndicator(
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                )
            }else{
                Icon(Icons.Filled.Refresh, contentDescription = "Refresh",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .clickable {

                        }
                )
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .weight(0.8f)) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)){
                items(3){
                    DevicesPageList(ip = "192.168.31.139", port = "6666")
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

}

@Composable
fun DevicesPageList(ip:String,port:String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
    ) {
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

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DevicesPagePreview() {
    DevicesPage()
}