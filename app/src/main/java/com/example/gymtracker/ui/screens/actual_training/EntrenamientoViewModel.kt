package com.example.gymtracker.ui.screens.actual_training

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.EntrenamientosRepository
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.ui.controllers.ControladorSesion

/**
 * ViewModel encargado de gestionar los entrenamientos del usuario logueado.
 *
 * Se encarga de:
 * - Cargar los entrenamientos desde el repositorio.
 * - Mantener el estado observable de los entrenamientos.
 * - Guardar o actualizar entrenamientos.
 *
 * @property repo Repositorio que gestiona el acceso a los entrenamientos
 * (lectura y escritura de datos).
 */
class EntrenamientoViewModel(
    private val repo: EntrenamientosRepository
) : ViewModel() {
    private val _entrenamientos = mutableStateListOf<Entreno>()
    val entrenamientos: List<Entreno> get() = _entrenamientos

    fun cargarDatos() {
        val usuario = ControladorSesion.usuarioLogueado()
        usuario?.let {
            val entrenos = repo.obtenerEntrenamientos(it.id)
            _entrenamientos.clear()
            _entrenamientos.addAll(entrenos)
        }
    }

    fun guardarEntrenamiento(entreno: Entreno) {
        val usuario = ControladorSesion.usuarioLogueado() ?: return
        val index = _entrenamientos.indexOfFirst { it.fecha == entreno.fecha }
        if (index >= 0) {
            _entrenamientos[index] = entreno
        } else {
            _entrenamientos.add(entreno)
        }
        repo.guardarEntrenamiento(usuario.id, entreno)
    }
}