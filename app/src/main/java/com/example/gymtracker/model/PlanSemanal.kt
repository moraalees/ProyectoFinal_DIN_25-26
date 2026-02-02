package com.example.gymtracker.model

import java.util.UUID

/**
 * Representa un plan de entrenamiento semanal.
 *
 * Contiene un identificador único, un nombre descriptivo y la lista de entrenamientos
 * para cada día de la semana.
 *
 * @property id Identificador único del plan.
 * @property nombre Nombre del plan semanal.
 * @property dias Lista de entrenamientos (Entreno) correspondientes a cada día del plan.
 */
data class PlanSemanal(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val dias: MutableList<Entreno>
)