package com.example.gymtracker.model

import androidx.annotation.DrawableRes
import com.example.gymtracker.model.enum_classes.Musculo
import com.example.gymtracker.model.enum_classes.TipoPeso
import java.io.Serializable

/**
 * Representa un ejercicio base con sus propiedades principales.
 *
 * @property id Identificador único del ejercicio.
 * @property nombre Nombre del ejercicio.
 * @property descripcion Descripción detallada del ejercicio.
 * @property tipoPeso Tipo de peso utilizado en el ejercicio.
 * @property musculos Lista de músculos que se trabajan con el ejercicio.
 * @property imagen Recurso drawable que representa visualmente el ejercicio.
 */
data class EjercicioBase(
    val id: Int,
    var nombre: String,
    var descripcion: String,
    val tipoPeso: TipoPeso,
    val musculos: List<Musculo>,
    @DrawableRes val imagen: Int
) : Serializable