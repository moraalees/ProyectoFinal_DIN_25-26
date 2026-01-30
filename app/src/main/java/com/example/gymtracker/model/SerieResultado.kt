package com.example.gymtracker.model

import java.io.Serializable

data class SerieResultado(
    val repeticiones: Int,
    val peso: Double
) : Serializable