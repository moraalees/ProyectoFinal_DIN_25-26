package com.example.gymtracker.model.enum_classes

/**
 * Enum que representa los diferentes tipos de rutinas de entrenamiento.
 *
 * Valores posibles:
 * - [FULL_BODY]: Rutina de cuerpo completo.
 * - [UPPER_LOWER]: Rutina dividida en tren superior e inferior.
 * - [PUSH_PULL_LEGS]: Rutina dividida en empuje, tracción y piernas.
 * - [DIVISION_GRUPOS_MUSCULARES]: Rutina dividida por grupos musculares específicos.
 * - [ALTA_FRECUENCIA]: Rutina con alta frecuencia de entrenamiento.
 * - [PERSONALIZADA]: Rutina personalizada según el usuario.
 */
enum class TipoRutina {
    FULL_BODY,
    UPPER_LOWER,
    PUSH_PULL_LEGS,
    DIVISION_GRUPOS_MUSCULARES,
    ALTA_FRECUENCIA,
    PERSONALIZADA
}