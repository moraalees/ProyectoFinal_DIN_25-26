package com.example.gymtracker.ui.controllers

import android.content.Context
import com.example.gymtracker.data.local.json.SesionJsonManager
import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.model.SesionPersistida
import com.example.gymtracker.model.Usuario

/**
 * Objeto singleton que controla el estado de sesión del usuario en la aplicación.
 *
 * Permite iniciar, cerrar y cargar sesiones, así como obtener y actualizar el usuario actualmente logueado.
 */
object ControladorSesion {
    private var usuarioActual: Usuario? = null

    fun iniciarSesion(context: Context, usuario: Usuario) {
        usuarioActual = usuario
        SesionJsonManager.guardar(context, SesionPersistida(usuarioActualId = usuario.id))
    }

    fun cerrarSesion(context: Context) {
        usuarioActual = null
        SesionJsonManager.borrarSesion(context)
    }

    fun cargarSesion(context: Context, dataSource: UsuarioJsonDataSource) {
        val sesion = SesionJsonManager.cargar(context)

        if (sesion?.usuarioActualId != null) {
            val usuario = dataSource.obtenerUsuarios().find { it.id == sesion.usuarioActualId }
            usuarioActual = usuario
        } else {
            usuarioActual = null
        }
    }

    fun actualizarUsuario(usuarioActualizado: Usuario) {
        usuarioActual = usuarioActualizado
    }

    fun usuarioLogueado(): Usuario? = usuarioActual
}