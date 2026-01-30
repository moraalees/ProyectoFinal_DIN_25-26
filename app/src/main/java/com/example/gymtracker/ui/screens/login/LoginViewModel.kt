package com.example.gymtracker.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.ui.controllers.ControladorSesion

class LoginViewModel(
    private val repositorioUsuario: UsuarioRepository,
    private val context: Context
) : ViewModel() {

    fun login(correo: String, contrasena: String): Boolean {
        val resultado = repositorioUsuario.iniciarSesion(correo, contrasena)
        return if (resultado.isSuccess) {
            ControladorSesion.iniciarSesion(context, resultado.getOrNull()!!)
            true
        } else {
            false
        }
    }
}