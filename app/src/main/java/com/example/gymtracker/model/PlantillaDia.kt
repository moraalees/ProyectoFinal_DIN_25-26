package com.example.gymtracker.model

data class PlantillaDia(
    val nombre: String,
    val ejerciciosIds: List<Int>
) : DiaPlanSemanal()
