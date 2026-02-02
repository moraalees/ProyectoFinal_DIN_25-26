package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.Enfoque
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

/**
 * Representa un entrenamiento compuesto por varios ejercicios planificados.
 *
 * @property id Identificador único del entrenamiento.
 * @property nombre Nombre del entrenamiento.
 * @property ejercicios Lista de ejercicios planificados para este entrenamiento.
 * @property enfoque Enfoque principal del entrenamiento (Hipertrofia, Definición, Perder Peso).
 * @property esEntreno Indica si es un día de entrenamiento real o un día de descanso.
 * @property tiempoDescanso Tiempo de descanso entre series en segundos.
 * @property fecha Fecha en la que se realiza o planifica el entrenamiento.
 */
data class Entreno(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    var ejercicios: MutableList<EjercicioPlan>,
    val enfoque: Enfoque,
    val esEntreno: Boolean = true,
    val tiempoDescanso: Int = 120,
    val fecha: String = LocalDate.now().toString()
) : Serializable