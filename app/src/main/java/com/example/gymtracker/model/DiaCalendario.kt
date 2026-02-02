package com.example.gymtracker.model

import java.time.LocalDate

/**
 * Representa un día en el calendario de entrenamientos.
 *
 * @property fecha La fecha del día.
 * @property esDelMesActual Indica si el día pertenece al mes actual mostrado.
 * @property esHoy Indica si el día corresponde a la fecha actual.
 * @property entrenos Lista de entrenamientos programados para ese día.
 */
data class DiaCalendario(
    val fecha: LocalDate,
    val esDelMesActual: Boolean,
    val esHoy: Boolean,
    val entrenos: List<Entreno> = emptyList()
)