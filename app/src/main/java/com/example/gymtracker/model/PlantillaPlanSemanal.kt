package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.enum_classes.TipoRutina

/**
 * Representa una plantilla de plan semanal de entrenamiento.
 *
 * Define el enfoque, la experiencia, el tipo de rutina y los días que componen el plan.
 * Asegura que la plantilla contenga exactamente 7 días.
 *
 * @property enfoque Enfoque principal del plan (Hipertrofia, Definición, Perder Peso).
 * @property experiencia Nivel de experiencia del usuario al que se dirige la plantilla.
 * @property tipoRutina Tipo de rutina del plan semanal (Full Body, Push/Pull/Legs, etc.).
 * @property dias Lista de días que conforman la semana, cada uno puede ser un día de entrenamiento o de descanso.
 */
data class PlantillaPlanSemanal(
    val enfoque: Enfoque,
    val experiencia: Experiencia,
    val tipoRutina: TipoRutina,
    val dias: List<DiaPlanSemanal>
) {
    init {
        require(dias.size == 7) {
            "Una plantilla semanal debe tener exactamente 7 días (tiene ${dias.size})"
        }
    }
}