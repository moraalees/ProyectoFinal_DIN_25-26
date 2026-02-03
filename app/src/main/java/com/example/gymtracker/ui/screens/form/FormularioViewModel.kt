package com.example.gymtracker.ui.screens.form

import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.model.enum_classes.Altura
import com.example.gymtracker.model.enum_classes.Edad
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.UsuarioGimnasio
import com.example.gymtracker.ui.controllers.ControladorSesion

/**
 * ViewModel encargado de gestionar el formulario de perfil de gimnasio del usuario.
 *
 * Se responsabiliza de:
 * - Recuperar el usuario actualmente autenticado desde el controlador de sesión.
 * - Crear o actualizar el perfil [UsuarioGimnasio] con los datos introducidos
 *   en el formulario.
 * - Persistir el perfil en el repositorio correspondiente.
 * - Asignar automáticamente una rutina inicial en función de los datos del perfil.
 *
 * Si no existe un usuario logueado, el guardado del perfil no se realiza y
 * el método devuelve `null`.
 *
 * @property repositorioUsuarioGimnasio Repositorio encargado de la gestión de
 * perfiles de gimnasio y la asignación de rutinas.
 */
class FormularioViewModel(
    private val repositorioUsuarioGimnasio: UsuarioGimnasioRepository
): ViewModel() {

    fun guardarPerfil(
        edad: Edad,
        altura: Altura,
        peso: Double,
        experiencia: Experiencia,
        enfoque: Enfoque
    ): UsuarioGimnasio? {
        val usuario = ControladorSesion.usuarioLogueado()
            ?: return null

        val cuenta = UsuarioGimnasio(
            usuarioId = usuario.id,
            edad = edad,
            altura = altura,
            peso = peso,
            experiencia = experiencia,
            enfoque = enfoque
        )

        repositorioUsuarioGimnasio.guardarPerfil(cuenta)
        repositorioUsuarioGimnasio.asignarRutina(cuenta)

        return repositorioUsuarioGimnasio.obtenerPerfilPorUsuario(usuario.id)
    }
}
