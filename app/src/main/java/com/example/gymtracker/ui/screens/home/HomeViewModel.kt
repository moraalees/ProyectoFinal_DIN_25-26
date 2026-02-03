package com.example.gymtracker.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.UsuarioGimnasio

/**
 * ViewModel encargado de manejar la pantalla principal (Home) del usuario.
 *
 * Su responsabilidad principal es cargar el perfil del usuario
 * y exponer la rutina semanal activa mediante un [mutableStateOf],
 * lo que permite que la UI de Compose se actualice automáticamente
 * cuando la rutina cambia.
 *
 * @property usuarioGimnasioRepository Repositorio que gestiona los datos del perfil de gimnasio del usuario.
 */
class HomeViewModel(
    private val usuarioGimnasioRepository: UsuarioGimnasioRepository
) : ViewModel() {

    var rutinaActiva by mutableStateOf<PlanSemanal?>(null)
        private set

    fun cargarPerfil(usuario: Usuario) {
        rutinaActiva = usuarioGimnasioRepository
            .obtenerPerfilPorUsuario(usuario.id)
            ?.rutinaActiva
    }
}