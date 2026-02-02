package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.TipoPeso
import java.time.LocalDate

/**
 * Representa una marca personal registrada para un ejercicio específico.
 *
 * @property id Identificador único de la marca.
 * @property ejercicioId ID del ejercicio asociado.
 * @property nombreEjercicio Nombre del ejercicio.
 * @property pesoMaximo Peso máximo alcanzado (puede ser null para ejercicios de peso corporal).
 * @property repeticionesMaximas Número máximo de repeticiones alcanzadas (opcional).
 * @property tipoPeso Tipo de peso utilizado en el ejercicio.
 * @property fecha Fecha en que se registró la marca personal.
 */
data class MarcaPersonal(
    val id: Int,
    val ejercicioId: Int,
    val nombreEjercicio: String,
    val pesoMaximo: Double?,
    val repeticionesMaximas: Int?,
    val tipoPeso: TipoPeso,
    val fecha: LocalDate
)