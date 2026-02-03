package com.example.gymtracker.ui.screens.training

import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.UsuarioGimnasio
import java.time.LocalDate

/**
 * ViewModel encargado de actualizar entrenamientos específicos dentro de las rutinas
 * del perfil de gimnasio de un usuario.
 *
 * @property repositorio Repositorio que permite acceder y modificar el perfil de gimnasio del usuario.
 */
class EntrenoEspecificoViewModel(
    private val repositorio: UsuarioGimnasioRepository
) : ViewModel() {

    private fun obtenerPerfil(usuarioId: Int): UsuarioGimnasio? {
        return repositorio.obtenerPerfilPorUsuario(usuarioId)
    }

    fun actualizarEntreno(
        usuario: Usuario,
        rutinaId: String,
        entrenoActualizado: Entreno
    ) {
        val perfil = obtenerPerfil(usuario.id) ?: return

        val rutinaIndex = perfil.rutinas.indexOfFirst { it.id == rutinaId }
        if (rutinaIndex == -1) return

        val rutina = perfil.rutinas[rutinaIndex]

        val diaIndex = rutina.dias.indexOfFirst { it.id == entrenoActualizado.id }
        if (diaIndex == -1) return

        val ejerciciosCopiados = entrenoActualizado.ejercicios.map { it.copy() }.toMutableList()

        val diaActualizado = entrenoActualizado.copy(
            ejercicios = ejerciciosCopiados,
            fecha = entrenoActualizado.fecha
        )

        val nuevaRutina = rutina.copy(
            dias = rutina.dias.toMutableList().apply { this[diaIndex] = diaActualizado }
        )

        perfil.rutinas[rutinaIndex] = nuevaRutina

        if (perfil.rutinaActiva?.id == rutina.id) {
            perfil.rutinaActiva = nuevaRutina
        }

        repositorio.guardarPerfil(perfil)
    }
}