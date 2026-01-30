package com.example.gymtracker.model

import androidx.annotation.DrawableRes
import com.example.gymtracker.model.enum_classes.Musculo
import com.example.gymtracker.model.enum_classes.TipoPeso
import java.io.Serializable

data class EjercicioBase(
    val id: Int,
    var nombre: String,
    var descripcion: String,
    val tipoPeso: TipoPeso,
    val musculos: List<Musculo>,
    @DrawableRes val imagen: Int
) : Serializable