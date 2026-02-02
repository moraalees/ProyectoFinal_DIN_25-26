package com.example.gymtracker.model

/**
 * Representa un usuario de la aplicación.
 *
 * @param id Identificador único del usuario.
 * @param nombre Nombre completo del usuario.
 * @param nombreUsuario Nombre de usuario para login o identificación.
 * @param contrasena Contraseña del usuario.
 * @param correo Correo electrónico del usuario.
 * @param esAdmin Indica si el usuario tiene privilegios de administrador.
 */
data class Usuario(
    val id: Int,
    val nombre: String,
    var nombreUsuario: String,
    var contrasena: String,
    var correo: String,
    val esAdmin: Boolean = false
)