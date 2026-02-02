package com.example.gymtracker.model.enum_classes

/**
 * Enum que representa los distintos músculos del cuerpo que pueden ser trabajados en los ejercicios.
 *
 * Valores posibles:
 * - [PECHO_INFERIOR], [PECHO_SUPERIOR]: Zonas del pecho.
 * - [BICEPS_CABEZA_LARGA], [BICEPS_CABEZA_CORTA]: Cabezas del bíceps.
 * - [TRICEPS_CABEZA_LARGA], [TRICEPS_CABEZA_LATERAL], [TRICEPS_CABEZA_MEDIAL]: Cabezas del tríceps.
 * - [DORSAL_ANCHO], [DORSAL_MEDIO]: Músculos de la espalda.
 * - [LUMBARES]: Zona lumbar.
 * - [CUADRICEPS], [GEMELO], [SOLEO], [ISQUIOS], [ABDUCTORES], [GLUTEOS]: Músculos de las piernas y glúteos.
 * - [DELTOIDES_ANTERIOR], [DELTOIDES_LATERAL], [DELTOIDES_POSTERIOR]: Deltoides del hombro.
 * - [BRAQUIORRADIAL], [TRAPECIO], [ABDOMINALES], [ANTEBRAZO]: Otros músculos específicos.
 */
enum class Musculo {
    PECHO_INFERIOR,
    PECHO_SUPERIOR,
    BICEPS_CABEZA_LARGA,
    BICEPS_CABEZA_CORTA,
    TRICEPS_CABEZA_LARGA,
    TRICEPS_CABEZA_LATERAL,
    TRICEPS_CABEZA_MEDIAL,
    DORSAL_ANCHO,
    DORSAL_MEDIO,
    LUMBARES,
    CUADRICEPS,
    GEMELO,
    SOLEO,
    ISQUIOS,
    ABDUCTORES,
    DELTOIDES_ANTERIOR,
    DELTOIDES_LATERAL,
    DELTOIDES_POSTERIOR,
    BRAQUIORRADIAL,
    TRAPECIO,
    GLUTEOS,
    ABDOMINALES,
    ANTEBRAZO
}