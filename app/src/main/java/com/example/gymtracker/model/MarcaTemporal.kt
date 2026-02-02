package com.example.gymtracker.model

import com.example.gymtracker.model.enum_classes.TipoPeso
import java.time.LocalDate

/**
 * Representa una marca temporal o registro provisional de un ejercicio.
 *
 * Se utiliza para guardar datos de rendimiento antes de confirmarlos como marca personal.
 *
 * @property ejercicioId ID del ejercicio asociado.
 * @property nombre Nombre del ejercicio.
 * @property pesoMax Peso máximo levantado en esta sesión.
 * @property repeticiones Número de repeticiones realizadas.
 * @property tipoPeso Tipo de peso utilizado en el ejercicio.
 * @property fecha Fecha en que se registró la marca temporal (puede ser null).
 */
data class MarcaTemporal(
    var ejercicioId: Int,
    var nombre: String,
    var pesoMax: Double,
    var repeticiones: Int,
    var tipoPeso: TipoPeso,
    var fecha: LocalDate?
)
