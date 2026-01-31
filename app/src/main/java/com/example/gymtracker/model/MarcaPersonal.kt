package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.TipoPeso
import java.time.LocalDate

data class MarcaPersonal(
    val id: Int,
    val ejercicioId: Int,
    val nombreEjercicio: String,
    val pesoMaximo: Double?,
    val repeticionesMaximas: Int?,
    val tipoPeso: TipoPeso,
    val fecha: LocalDate
)