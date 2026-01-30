package com.example.gymtracker.model


data class ParametrosEntreno(
    val series: Int,
    val repeticiones: IntRange,
    val minutosDescanso: Double
)