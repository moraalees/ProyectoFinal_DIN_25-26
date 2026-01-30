package com.example.gymtracker.ui.screens.form

import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioGimnasioRepository
import com.example.gymtracker.model.enum_classes.Altura
import com.example.gymtracker.model.enum_classes.Edad
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.UsuarioGimnasio
import com.example.gymtracker.ui.controllers.ControladorSesion

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

    fun tienePerfil(usuarioId: Int): Boolean {
        val perfil = repositorioUsuarioGimnasio.obtenerPerfilPorUsuario(usuarioId)
        return perfil != null
    }
}
