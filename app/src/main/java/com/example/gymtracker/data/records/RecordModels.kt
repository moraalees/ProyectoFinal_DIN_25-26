package com.example.gymtracker.data.records

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
