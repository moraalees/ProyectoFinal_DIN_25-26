package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.enum_classes.TipoRutina

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