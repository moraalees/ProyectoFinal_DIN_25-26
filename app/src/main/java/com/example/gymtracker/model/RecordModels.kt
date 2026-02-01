package com.example.gymtracker.model

import com.google.gson.annotations.SerializedName

data class RecordSubmission(
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("peso") val peso: Double,
    @SerializedName("repeticiones") val repeticiones: Int,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("videoPath") val videoPath: String? = null
)

data class RecordExerciseEntry(
    @SerializedName("ejercicioId") val ejercicioId: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("tops") val tops: List<RecordSubmission> = emptyList(),
    @SerializedName("updatedAt") val updatedAt: String? = null
)

data class RecordsFile(
    @SerializedName("exercises") val exercises: List<RecordExerciseEntry> = emptyList()
)

// Nuevo: modelo de solicitud de marca
data class RecordRequest(
    @SerializedName("id") val id: String,
    @SerializedName("ejercicioId") val ejercicioId: Int,
    @SerializedName("submission") val submission: RecordSubmission,
    @SerializedName("videoPath") val videoPath: String // ruta relativa dentro de filesDir (p. ej. "requests/videos/ejercicio_...mp4")
)

data class RequestsFile(
    @SerializedName("requests") val requests: List<RecordRequest> = emptyList()
)
