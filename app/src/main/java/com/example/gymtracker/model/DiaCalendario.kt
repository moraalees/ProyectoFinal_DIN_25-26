package com.example.gymtracker.model

import java.time.LocalDate

data class DiaCalendario(
    val fecha: LocalDate,
    val esDelMesActual: Boolean,
    val esHoy: Boolean,
    val entrenos: List<Entreno> = emptyList()
)