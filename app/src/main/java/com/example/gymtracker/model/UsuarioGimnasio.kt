package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.Altura
import com.example.gymtracker.model.enum_classes.Edad
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia

/**
 * Representa el perfil de un usuario dentro del gimnasio.
 *
 * @param usuarioId ID del usuario asociado.
 * @param edad Rango de edad del usuario.
 * @param altura Rango de altura del usuario.
 * @param peso Peso actual del usuario en kilogramos.
 * @param experiencia Nivel de experiencia en entrenamiento.
 * @param enfoque Objetivo principal del entrenamiento (hipertrofia, definición, perder peso).
 * @param rutinas Lista de planes semanales asignados al usuario.
 * @param rutinaActiva Plan semanal actualmente activo, si existe.
 */
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