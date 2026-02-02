package com.example.gymtracker.model

import java.io.Serializable

/**
 * Representa un ejercicio dentro de un plan de entrenamiento específico.
 *
 * Contiene información sobre las series, repeticiones y peso estimado,
 * así como los resultados de las series realizadas.
 *
 * @property ejercicio El ejercicio base asociado.
 * @property series Número de series planificadas.
 * @property repeticiones Rango de repeticiones por serie.
 * @property pesoEstimado Peso estimado a utilizar en el ejercicio (puede ser null para peso corporal).
 * @property seriesRealizadas Lista de resultados de las series completadas.
 */
data class EjercicioPlan(
    val ejercicio: EjercicioBase,
    var series: Int,
    var repeticiones: IntRange,
    var pesoEstimado: Double?,
    val seriesRealizadas: MutableList<SerieResultado> = mutableListOf()
) : Serializable {
    fun copiaGuardada(): EjercicioPlan {
        return EjercicioPlan(
            ejercicio = ejercicio,
            series = series,
            repeticiones = repeticiones,
            pesoEstimado = pesoEstimado,
            seriesRealizadas = seriesRealizadas.toMutableList()
        )
    }
}
