package com.example.gymtracker.ui.screens.userdata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.UsuarioGimnasio
import com.example.gymtracker.ui.controllers.ControladorSesion

class DatosViewModel (
    private val usuarioRepository: UsuarioRepository,
    private val usuarioGimnasioRepository: UsuarioGimnasioRepository
): ViewModel() {

    var usuario by mutableStateOf<Usuario?>(null)
        private set
    var perfilGimnasio by mutableStateOf<UsuarioGimnasio?>(null)
        private set

    fun cargarDatos(usuarioBase: Usuario) {
        usuario = usuarioBase
        perfilGimnasio = usuarioGimnasioRepository
            .obtenerPerfilPorUsuario(usuarioBase.id)
    }

    fun cambiarNombre(nuevoNombre: String) {
        val u = usuario ?: return

        usuarioRepository.cambiarNombre(nuevoNombre, u)
            .onSuccess { usuarioActualizado ->
                usuario = usuarioActualizado
                ControladorSesion.actualizarUsuario(usuarioActualizado)
            }
    }

    fun cambiarNombreUsuario(nuevo: String){
        val u = usuario ?: return

        usuarioRepository.cambiarNombreUsuario(nuevo, u)
            .onSuccess { usuarioActualizado ->
                usuario = usuarioActualizado
                ControladorSesion.actualizarUsuario(usuarioActualizado)
            }
    }

    fun cambiarCorreo(nuevo: String) {
        val u = usuario ?: return

        usuarioRepository.cambiarCorreo(nuevo, u)
            .onSuccess { usuarioActualizado ->
                usuario = usuarioActualizado
                ControladorSesion.actualizarUsuario(usuarioActualizado)
            }
    }

    fun cambiarContrasena(nueva: String) {
        val u = usuario ?: return

        usuarioRepository.cambiarContrasena(nueva, u)
            .onSuccess { usuarioActualizado ->
                usuario = usuarioActualizado
                ControladorSesion.actualizarUsuario(usuarioActualizado)
            }
    }


    fun guardarPerfilGimnasio(actualizado: UsuarioGimnasio) {
        usuarioGimnasioRepository.guardarPerfil(actualizado)
        perfilGimnasio = actualizado
    }
}