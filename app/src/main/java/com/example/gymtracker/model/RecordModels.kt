package com.example.gymtracker.model

import com.google.gson.annotations.SerializedName

/**
 * Representa una marca personal enviada por un usuario para un ejercicio.
 *
 * Contiene el ID del usuario, peso levantado, repeticiones realizadas, fecha y ruta opcional del video.
 */
data class RecordSubmission(
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("peso") val peso: Double,
    @SerializedName("repeticiones") val repeticiones: Int,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("videoPath") val videoPath: String? = null
)

/**
 * Representa los registros top de un ejercicio específico.
 *
 * Contiene el ID del ejercicio, nombre, lista de mejores marcas y la fecha de última actualización.
 */
data class RecordExerciseEntry(
    @SerializedName("ejercicioId") val ejercicioId: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("tops") val tops: List<RecordSubmission> = emptyList(),
    @SerializedName("updatedAt") val updatedAt: String? = null
)

/**
 * Archivo que contiene todos los registros de ejercicios.
 *
 * Cada entrada corresponde a un ejercicio con sus marcas top.
 */
data class RecordsFile(
    @SerializedName("exercises") val exercises: List<RecordExerciseEntry> = emptyList()
)

/**
 * Representa una solicitud de registro realizada por un usuario.
 *
 * Contiene un ID único, el ID del ejercicio, la marca enviada y la ruta del video asociado.
 */
data class RecordRequest(
    @SerializedName("id") val id: String,
    @SerializedName("ejercicioId") val ejercicioId: Int,
    @SerializedName("submission") val submission: RecordSubmission,
    @SerializedName("videoPath") val videoPath: String
)

/**
 * Archivo que contiene todas las solicitudes de registro pendientes.
 */
data class RequestsFile(
    @SerializedName("requests") val requests: List<RecordRequest> = emptyList()
)
