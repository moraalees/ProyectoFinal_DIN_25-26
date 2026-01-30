package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.Altura
import com.example.gymtracker.model.enum_classes.Edad
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia

data class UsuarioGimnasio(
    val usuarioId: Int,
    var edad: Edad,
    var altura: Altura,
    var peso: Double,
    var experiencia: Experiencia,
    var enfoque: Enfoque,
    var rutinas: MutableList<PlanSemanal> = mutableListOf(),
    var rutinaActiva: PlanSemanal? = null
)