package com.example.gymtracker.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.UsuarioGimnasio

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

    fun actualizarRutinaActiva(rutina: PlanSemanal) {
        rutinaActiva = rutina
    }
}