package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.TipoPeso
import java.time.LocalDate

data class MarcaTemporal(
    var ejercicioId: Int,
    var nombre: String,
    var pesoMax: Double,
    var repeticiones: Int,
    var tipoPeso: TipoPeso,
    var fecha: LocalDate?
)
