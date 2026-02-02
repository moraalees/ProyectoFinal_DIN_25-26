package com.example.gymtracker.model

/**
 * Contiene los parámetros planificados de un entrenamiento.
 *
 * @property series Número de series a realizar.
 * @property repeticiones Rango de repeticiones por serie.
 * @property minutosDescanso Tiempo de descanso entre series en minutos.
 */
data class ParametrosEntreno(
    val series: Int,
    val repeticiones: IntRange,
    val minutosDescanso: Double
)