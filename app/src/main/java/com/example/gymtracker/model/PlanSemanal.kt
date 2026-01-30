package com.example.gymtracker.model

import java.util.UUID

data class PlanSemanal(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val dias: MutableList<Entreno>
)