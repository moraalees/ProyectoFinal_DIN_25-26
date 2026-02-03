package com.example.gymtracker.ui.screens.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.data.repository.EntrenamientosRepository
import com.example.gymtracker.model.MarcaPersonal
import com.example.gymtracker.model.MarcaTemporal
import com.example.gymtracker.model.SerieResultado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import com.example.gymtracker.model.enum_classes.TipoPeso

/**
 * MarcasUiState
 *
 * Representa los distintos estados de la UI para la pantalla de marcas personales.
 */
sealed class MarcasUiState {
    object Loading : MarcasUiState()
    data class Success(val marcas: List<MarcaPersonal>) : MarcasUiState()
    data class Error(val mensaje: String) : MarcasUiState()
}


/**
 * MarcasViewModel
 *
 * ViewModel encargado de obtener y procesar las marcas personales de un usuario
 * a partir de sus entrenamientos registrados.
 *
 * Funcionalidad:
 * - Carga los entrenamientos de un usuario por su ID.
 * - Procesa las series de cada ejercicio para determinar el peso máximo y repeticiones.
 * - Genera una lista de `MarcaPersonal` ordenada por peso máximo.
 * - Expone el estado mediante un StateFlow `uiState` para que la UI pueda reaccionar.
 *
 * Propiedades:
 * @param entrenamientosRepository Repositorio que permite acceder a los entrenamientos del usuario.
 *
 * Métodos principales:
 * - cargarMarcas(usuarioId: Int): Obtiene entrenamientos y calcula las marcas personales.
 *
 * Funciones internas:
 * - procesarSerie(): Actualiza la marca máxima de un ejercicio en un mapa temporal.
 */
class MarcasViewModel(
    private val entrenamientosRepository: EntrenamientosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MarcasUiState>(MarcasUiState.Loading)
    val uiState: StateFlow<MarcasUiState> = _uiState

    fun cargarMarcas(usuarioId: Int) {
        _uiState.value = MarcasUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val entrenos = entrenamientosRepository.obtenerEntrenamientos(usuarioId)

                val map = mutableMapOf<Int, MarcaTemporal>()

                val formatter = DateTimeFormatter.ISO_LOCAL_DATE

                for (entreno in entrenos) {
                    val fechaLocal: LocalDate? = try {
                        LocalDate.parse(entreno.fecha, formatter)
                    } catch (_: DateTimeParseException) {
                        null
                    }

                    val ejercicios = entreno.ejercicios

                    for (ejPlan in ejercicios) {
                        val ejercicioId = ejPlan.ejercicio.id
                        val nombreEj = ejPlan.ejercicio.nombre
                        val tipoPeso = ejPlan.ejercicio.tipoPeso

                        val series = ejPlan.seriesRealizadas

                        for (serie in series) {
                            procesarSerie(map, ejercicioId, nombreEj, tipoPeso, serie, fechaLocal)
                        }
                    }
                }

                val marcas = map.values.map {
                    MarcaPersonal(
                        id = it.ejercicioId,
                        ejercicioId = it.ejercicioId,
                        nombreEjercicio = it.nombre,
                        pesoMaximo = it.pesoMax,
                        repeticionesMaximas = it.repeticiones,
                        tipoPeso = it.tipoPeso,
                        fecha = it.fecha ?: LocalDate.now()
                    )
                }.sortedByDescending { it.pesoMaximo ?: 0.0 }

                _uiState.value = MarcasUiState.Success(marcas)

            } catch (e: Exception) {
                _uiState.value = MarcasUiState.Error("Error cargando marcas: ${e.message}")
            }
        }
    }


    private fun procesarSerie(
        map: MutableMap<Int, MarcaTemporal>,
        ejercicioId: Int,
        nombreEj: String,
        tipoPeso: TipoPeso,
        serie: SerieResultado,
        fechaLocal: LocalDate?
    ) {
        val peso = serie.peso
        val reps = serie.repeticiones

        val existente = map[ejercicioId]
        if (existente == null) {
            map[ejercicioId] = MarcaTemporal(
                ejercicioId = ejercicioId,
                nombre = nombreEj,
                pesoMax = peso,
                repeticiones = reps,
                tipoPeso = tipoPeso,
                fecha = fechaLocal
            )
        } else {
            if (peso > existente.pesoMax) {
                existente.pesoMax = peso
                existente.repeticiones = reps
                existente.fecha = fechaLocal
            } else if (peso == existente.pesoMax) {
                if (fechaLocal != null) {
                    val fechaExistente = existente.fecha
                    if (fechaExistente == null || fechaLocal.isAfter(fechaExistente)) {
                        existente.repeticiones = reps
                        existente.fecha = fechaLocal
                    }
                }
            }
        }
    }
}
