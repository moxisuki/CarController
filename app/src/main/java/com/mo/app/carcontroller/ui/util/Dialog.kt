package com.mo.app.carcontroller.ui.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun InputDialog(showDialog:MutableState<Boolean>,ip:MutableState<String>,port:MutableState<String>,tips1:String, tips2:String, onConfirm: (String, String) -> Unit, onCancel: () -> Unit){


}