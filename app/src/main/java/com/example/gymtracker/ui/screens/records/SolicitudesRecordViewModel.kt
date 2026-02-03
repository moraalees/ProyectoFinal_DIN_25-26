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

/**
 * ViewModel para manejar las solicitudes de registro de records de ejercicios.
 *
 * Funcionalidades:
 * - loadRequests(): carga todas las solicitudes pendientes desde el repositorio.
 * - accept(requestId): acepta una solicitud y recarga la lista.
 * - reject(requestId): rechaza una solicitud y recarga la lista.
 *
 * Estados:
 * - solicitudes: StateFlow que expone la lista actual de solicitudes.
 */
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
