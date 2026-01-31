package com.example.gymtracker.data.records

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecordsRepository(private val dataSource: LocalRecordsDataSource) {

    private fun compareSubmissions(a: RecordSubmission, b: RecordSubmission): Int {
        // orden: peso desc, si empate repeticiones desc
        return when {
            a.peso > b.peso -> -1
            a.peso < b.peso -> 1
            else -> {
                when {
                    a.repeticiones > b.repeticiones -> -1
                    a.repeticiones < b.repeticiones -> 1
                    else -> 0
                }
            }
        }
    }

    suspend fun getTopsForExercise(ejercicioId: Int): List<RecordSubmission> = withContext(Dispatchers.IO) {
        val file = dataSource.loadAllRecords()
        val entry = file.exercises.find { it.ejercicioId == ejercicioId }
        entry?.tops ?: emptyList()
    }

    suspend fun wouldEnterTop3(ejercicioId: Int, candidate: RecordSubmission): Boolean = withContext(Dispatchers.Default) {
        val current = getTopsForExercise(ejercicioId)
        val mutable = current.toMutableList()
        mutable.add(candidate)
        mutable.sortWith { a, b -> compareSubmissions(a, b) }
        val index = mutable.indexOf(candidate)
        index in 0..2
    }

    data class SubmitResult(val success: Boolean, val message: String? = null, val newIndex: Int? = null, val savedVideoPath: String? = null)

    suspend fun submitMarca(
        ejercicioId: Int,
        usuarioId: Int,
        candidate: RecordSubmission,
        videoUri: Uri?
    ): SubmitResult = withContext(Dispatchers.IO) {
        if (videoUri == null) {
            return@withContext SubmitResult(false, "Se requiere un vídeo para enviar la marca")
        }

        val enters = wouldEnterTop3(ejercicioId, candidate)
        if (!enters) {
            return@withContext SubmitResult(false, "La marca no entra en el top3")
        }

        val file = dataSource.loadAllRecords()
        val exercises = file.exercises.toMutableList()
        var entry = exercises.find { it.ejercicioId == ejercicioId }

        val now = DateTimeFormatter.ISO_INSTANT.format(Instant.now().atOffset(ZoneOffset.UTC))

        if (entry == null) {
            entry = RecordExerciseEntry(
                ejercicioId = ejercicioId,
                nombre = candidate.usuarioId.toString(),
                tops = listOf(),
                updatedAt = now
            )
            exercises.add(entry)
        }

        val topsMutable = entry.tops.toMutableList()

        val simulated = topsMutable.toMutableList()
        simulated.add(candidate)
        simulated.sortWith { a, b -> compareSubmissions(a, b) }
        val newIndex = simulated.indexOf(candidate)

        var savedVideoPath: String? = null
        if (newIndex == 0) {
            val path = dataSource.saveVideoForExercise(ejercicioId, candidate.usuarioId, videoUri)
            if (path == null) {
                return@withContext SubmitResult(false, "Error guardando el vídeo")
            }
            savedVideoPath = path
        }

        val candidateWithVideo = if (savedVideoPath != null) candidate.copy(videoPath = savedVideoPath) else candidate.copy(videoPath = null)

        topsMutable.add(candidateWithVideo)
        topsMutable.sortWith { a, b -> compareSubmissions(a, b) }
        val limited = topsMutable.take(10)

        val processedTops = limited.mapIndexed { idx, sub ->
            if (idx == 0) {
                sub
            } else {
                if (sub.videoPath != null) {
                    try {
                        dataSource.deleteVideo(sub.videoPath)
                    } catch (_: Exception) {
                    }
                    sub.copy(videoPath = null)
                } else sub
            }
        }

        val newTopVideo = processedTops.firstOrNull()?.videoPath
        val oldVideoPaths = topsMutable.mapNotNull { it.videoPath }.toSet()
        for (old in oldVideoPaths) {
            if (old != newTopVideo) {
                try {
                    dataSource.deleteVideo(old)
                } catch (_: Exception) {
                }
            }
        }

        val newEntry = RecordExerciseEntry(
            ejercicioId = entry.ejercicioId,
            nombre = entry.nombre,
            tops = processedTops,
            updatedAt = now
        )

        val newList = exercises.map { if (it.ejercicioId == ejercicioId) newEntry else it }
        val newFile = RecordsFile(exercises = newList)
        dataSource.saveAllRecords(newFile)

        SubmitResult(true, "Marca subida", newIndex, savedVideoPath)
    }
}
