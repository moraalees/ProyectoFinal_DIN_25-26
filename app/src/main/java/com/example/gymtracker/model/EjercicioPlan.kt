package com.example.gymtracker.model

import java.io.Serializable



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
