package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.Enfoque
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

data class Entreno(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    var ejercicios: MutableList<EjercicioPlan>,
    val enfoque: Enfoque,
    val esEntreno: Boolean = true,
    val tiempoDescanso: Int = 120,
    val fecha: String = LocalDate.now().toString()
) : Serializable