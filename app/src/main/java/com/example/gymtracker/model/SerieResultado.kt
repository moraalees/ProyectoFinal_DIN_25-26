package com.example.gymtracker.model

import java.io.Serializable

/**
 * Define los parámetros de un entrenamiento para un ejercicio o plan.
 *
 * @param series Número de series a realizar.
 * @param repeticiones Rango de repeticiones por serie.
 * @param minutosDescanso Minutos de descanso entre series.
 */
data class SerieResultado(
    val repeticiones: Int,
    val peso: Double
) : Serializable