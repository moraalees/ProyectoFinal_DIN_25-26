package com.example.gymtracker.model

/**
 * Representa la sesión persistida del usuario.
 *
 * @param usuarioActualId ID del usuario que está actualmente logueado, o null si no hay sesión activa.
 */
data class SesionPersistida(
    val usuarioActualId: Int? = null
)