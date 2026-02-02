package com.example.gymtracker.model

/**
 * Representa un día de entrenamiento dentro de una plantilla de plan semanal.
 *
 * Contiene el nombre del día y la lista de IDs de los ejercicios planificados.
 *
 * @property nombre Nombre descriptivo del día (ej. "Día 1 Full Body").
 * @property ejerciciosIds Lista de IDs de los ejercicios incluidos en este día.
 */
data class PlantillaDia(
    val nombre: String,
    val ejerciciosIds: List<Int>
) : DiaPlanSemanal()
