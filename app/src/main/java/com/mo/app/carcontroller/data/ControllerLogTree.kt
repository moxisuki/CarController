package com.mo.app.carcontroller.data

import com.mo.app.carcontroller.page.controller.ControllerViewModel
import timber.log.Timber

class ControllerLogTree(val viewModel: ControllerViewModel): Timber.Tree(){
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val _logs = viewModel.uiState.logs.toMutableList()
        _logs.add(message)
        viewModel.uiState = viewModel.uiState.copy(logs = _logs)
    }
}