package com.example.gymtracker.ui.screens.records

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.data.records.RecordSubmission
import com.example.gymtracker.data.records.RecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RecordsUiState {
    object Loading : RecordsUiState()
    data class Success(val ejercicios: List<com.example.gymtracker.data.records.RecordExerciseEntry>) : RecordsUiState()
    data class Error(val mensaje: String) : RecordsUiState()
}

sealed class RecordsEvent {
    data class SubmissionResult(val success: Boolean, val mensaje: String?) : RecordsEvent()
}

class RecordsViewModel(private val repository: RecordsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RecordsUiState>(RecordsUiState.Loading)
    val uiState: StateFlow<RecordsUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RecordsEvent>()
    val events: SharedFlow<RecordsEvent> = _events.asSharedFlow()

    fun loadTopsForAll() {
        _uiState.value = RecordsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ejerciciosOficiales = com.example.gymtracker.data.repository.EjerciciosRepository.obtenerEjerciciosPrincipales()

                val list = ejerciciosOficiales.map { ej ->
                    val tops = repository.getTopsForExercise(ej.id)
                    com.example.gymtracker.data.records.RecordExerciseEntry(
                        ejercicioId = ej.id,
                        nombre = ej.nombre,
                        tops = tops,
                        updatedAt = null
                    )
                }

                _uiState.value = RecordsUiState.Success(list)
            } catch (e: Exception) {
                _uiState.value = RecordsUiState.Error("Error cargando tops: ${e.message}")
            }
        }
    }

    fun wouldEnterTop3(ejercicioId: Int, candidate: RecordSubmission, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.wouldEnterTop3(ejercicioId, candidate)
            onResult(result)
        }
    }

    fun attemptSubmit(ejercicioId: Int, usuarioId: Int, candidate: RecordSubmission, videoUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.submitMarca(ejercicioId, usuarioId, candidate, videoUri)
            _events.emit(RecordsEvent.SubmissionResult(res.success, res.message))
            if (res.success) loadTopsForAll()
        }
    }
}
