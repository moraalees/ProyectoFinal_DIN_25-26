package com.example.gymtracker.model

/**
 * Representa un día seleccionado en la interfaz de usuario.
 *
 * @property label Nombre o descripción del día.
 * @property activo Indica si el día está activo o seleccionado.
 */
data class DiaSeleccion(
    val label: String,
    val activo: Boolean
)