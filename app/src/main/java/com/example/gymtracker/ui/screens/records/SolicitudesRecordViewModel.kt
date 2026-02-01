package com.example.gymtracker.ui.screens.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.model.RecordRequest
import com.example.gymtracker.data.repository.RecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SolicitudesRecordViewModel(
    private val repository: RecordsRepository
): ViewModel() {

    private val _solicitudes = MutableStateFlow<List<RecordRequest>>(emptyList())
    val solicitudes: StateFlow<List<RecordRequest>> = _solicitudes.asStateFlow()

    fun loadRequests() {
        viewModelScope.launch(Dispatchers.IO) {
            val file = repository.loadAllRequests()
            _solicitudes.value = file.requests
        }
    }

    fun accept(requestId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.adminAcceptRequest(requestId)
            loadRequests()
        }
    }

    fun reject(requestId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.adminRejectRequest(requestId)
            loadRequests()
        }
    }
}
