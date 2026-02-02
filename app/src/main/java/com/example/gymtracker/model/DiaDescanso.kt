package com.example.gymtracker.model

/**
 * Representa un día de descanso dentro de un plan semanal de entrenamiento.
 *
 * @property motivo Razón o descripción del descanso (por defecto "Descanso").
 */
data class DiaDescanso(
    val motivo: String = "Descanso"
) : DiaPlanSemanal()