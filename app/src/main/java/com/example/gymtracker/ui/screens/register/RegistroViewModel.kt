package com.example.gymtracker.ui.screens.register

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.ui.controllers.ControladorSesion

class RegistroViewModel(
    private val repositorioUsuario: UsuarioRepository,
    private val context: Context
) : ViewModel() {
    fun registrar(
        nombre: String,
        nombreUsuario: String,
        correo: String,
        contrasena: String
    ): String? {
        val resultado = repositorioUsuario.registrarUsuario(nombre, nombreUsuario, correo, contrasena)

        return if (resultado.isSuccess) {
            ControladorSesion.iniciarSesion(context, resultado.getOrNull()!!)
            null
        } else {
            resultado.exceptionOrNull()?.message
        }
    }
}
