package com.example.gymtracker.data.repository

import com.example.gymtracker.data.local.json.UsuarioGimnasioJsonDataSource
import com.example.gymtracker.model.UsuarioGimnasio

/**
 * Repositorio para manejar los perfiles de usuarios de gimnasio.
 *
 * Este repositorio permite:
 * - Guardar o actualizar un perfil de usuario.
 * - Obtener un perfil por su ID de usuario.
 * - Asignar planes de entrenamiento a un perfil.
 *
 * Funciones principales:
 * - [guardarPerfil]: Guarda o actualiza un perfil de usuario.
 * - [obtenerPerfilPorUsuario]: Obtiene un perfil según el ID de usuario.
 * - [asignarRutina]: Asigna un plan semanal generado al perfil del usuario.
 */
class UsuarioGimnasioRepository(
    private val usuarioGimnasioDS: UsuarioGimnasioJsonDataSource
) {

    fun guardarPerfil(perfil: UsuarioGimnasio) {
        val perfiles = usuarioGimnasioDS.obtenerPerfiles().toMutableList()
        val index = perfiles.indexOfFirst { it.usuarioId == perfil.usuarioId }

        if (index != -1) {
            perfiles[index] = perfil
        } else {
            perfiles.add(perfil)
        }
        usuarioGimnasioDS.guardarPerfiles(perfiles)
    }

    fun obtenerPerfilPorUsuario(usuarioId: Int): UsuarioGimnasio? {
        return usuarioGimnasioDS.obtenerPerfiles()
            .find { it.usuarioId == usuarioId }
    }

    fun asignarRutina(perfil: UsuarioGimnasio) {
        val plan = EntrenoRepository.generarPlanSemanal(perfil)

        val perfiles = usuarioGimnasioDS.obtenerPerfiles().toMutableList()
        val index = perfiles.indexOfFirst { it.usuarioId == perfil.usuarioId }

        if (index != -1) {
            val usuarioGuardado = perfiles[index]
            usuarioGuardado.rutinas.add(plan)

            if (usuarioGuardado.rutinaActiva == null) {
                usuarioGuardado.rutinaActiva = plan
            }
        }
        usuarioGimnasioDS.guardarPerfiles(perfiles)
    }
}