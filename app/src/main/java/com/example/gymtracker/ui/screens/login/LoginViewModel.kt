package com.example.gymtracker.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.ui.controllers.ControladorSesion

/**
 * LoginViewModel
 *
 * Maneja la lógica de inicio de sesión de usuarios.
 *
 * @param repositorioUsuario Repositorio para la autenticación de usuarios.
 * @param context Contexto de la aplicación, necesario para manejar la sesión.
 *
 * Funcionalidad:
 * - login(correo, contrasena): Intenta iniciar sesión con las credenciales proporcionadas.
 *   - Si la autenticación es exitosa, guarda la sesión actual mediante ControladorSesion.
 *   - Retorna `true` si el login fue exitoso, `false` en caso contrario.
 */
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