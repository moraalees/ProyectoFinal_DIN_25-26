package com.example.gymtracker.data.repository

import android.net.Uri
import com.example.gymtracker.data.local.json.LocalRecordsDataSource
import com.example.gymtracker.model.RecordExerciseEntry
import com.example.gymtracker.model.RecordRequest
import com.example.gymtracker.model.RecordSubmission
import com.example.gymtracker.model.RecordsFile
import com.example.gymtracker.model.RequestsFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Repositorio para manejar los records y solicitudes de ejercicios.
 *
 * Este repositorio permite:
 * - Obtener los mejores registros (tops) de un ejercicio.
 * - Evaluar si una nueva marca podría entrar al top 3.
 * - Crear solicitudes de nuevo record con video.
 * - Administrar la aceptación o rechazo de solicitudes.
 *
 * Funciones principales:
 * - [getTopsForExercise]: Obtiene los mejores registros para un ejercicio.
 * - [wouldEnterTop3]: Determina si una nueva marca entraría al top 3.
 * - [createRequest]: Crea una solicitud de nuevo record con video.
 * - [loadAllRequests]: Carga todas las solicitudes pendientes.
 * - [adminAcceptRequest]: Acepta una solicitud y actualiza records.
 * - [adminRejectRequest]: Rechaza una solicitud y elimina su video.
 * - Funciones privadas: [compareSubmissions], [isSameSubmission] usadas internamente para orden y comparación.
 */
class RecordsRepository(private val dataSource: LocalRecordsDataSource) {
    suspend fun getTopsForExercise(ejercicioId: Int): List<RecordSubmission> =
        withContext(Dispatchers.IO) {
            val file = dataSource.loadAllRecords()
            val entry = file.exercises.firstOrNull { it.ejercicioId == ejercicioId }
            entry?.tops ?: emptyList()
        }

    suspend fun wouldEnterTop3(ejercicioId: Int, candidate: RecordSubmission): Boolean =
        withContext(Dispatchers.Default) {
            val file = dataSource.loadAllRecords()
            val entry = file.exercises.firstOrNull { it.ejercicioId == ejercicioId }
            val tops = entry?.tops ?: emptyList()
            if (tops.size < 3) return@withContext true
            val worst =
                tops.sortedWith(compareByDescending<RecordSubmission> { it.peso }.thenByDescending { it.repeticiones })
                    .take(3).last()
            return@withContext compareSubmissions(candidate, worst) > 0
        }

    suspend fun createRequest(ejercicioId: Int, submission: RecordSubmission, videoUri: Uri): String? =
        withContext(Dispatchers.IO) {
            val savedPath = dataSource.saveRequestVideo(ejercicioId, submission.usuarioId, videoUri)
            if (savedPath == null) return@withContext null

            val id = UUID.randomUUID().toString()
            val req = RecordRequest(
                id = id,
                ejercicioId = ejercicioId,
                submission = submission.copy(videoPath = null),
                videoPath = savedPath
            )

            val requestsFile = dataSource.loadAllRequests()
            val newList = requestsFile.requests.toMutableList()
            newList.add(req)
            dataSource.saveAllRequests(RequestsFile(newList))
            return@withContext id
        }

    suspend fun loadAllRequests(): RequestsFile = withContext(Dispatchers.IO) {
        dataSource.loadAllRequests()
    }

    suspend fun adminAcceptRequest(requestId: String) = withContext(Dispatchers.IO) {
        val requestsFile = dataSource.loadAllRequests()
        val req = requestsFile.requests.firstOrNull { it.id == requestId } ?: return@withContext

        val movedVideoPath = dataSource.moveRequestVideoToVideos(
            req.videoPath,
            req.ejercicioId,
            req.submission.usuarioId
        )

        val recordsFile = dataSource.loadAllRecords()
        val entries = recordsFile.exercises.toMutableList()
        val index = entries.indexOfFirst { it.ejercicioId == req.ejercicioId }
        val existingEntry = if (index >= 0) entries[index] else null

        val mutableTops = existingEntry?.tops?.toMutableList() ?: mutableListOf()

        val newSubmission = req.submission.copy(videoPath = null)
        mutableTops.add(newSubmission)

        val sorted =
            mutableTops.sortedWith(compareByDescending<RecordSubmission> { it.peso }.thenByDescending { it.repeticiones })
                .take(3).toMutableList()

        val first = sorted.firstOrNull()
        var newAssigned = false
        if (movedVideoPath != null && first != null && isSameSubmission(first, newSubmission)) {
            sorted[0] = first.copy(videoPath = movedVideoPath)
            newAssigned = true
        }

        if (movedVideoPath != null && !newAssigned) {
            dataSource.deleteVideo(movedVideoPath)
        }

        val previousFirst = existingEntry?.tops?.firstOrNull()
        if (previousFirst != null) {
            val stillFirst =
                sorted.firstOrNull()?.let { isSameSubmission(it, previousFirst) } ?: false
            if (!stillFirst) {
                previousFirst.videoPath?.let { dataSource.deleteVideo(it) }
            }
        }

        val newEntry = RecordExerciseEntry(
            ejercicioId = req.ejercicioId,
            nombre = existingEntry?.nombre ?: "",
            tops = sorted,
            updatedAt = null
        )

        if (index >= 0) {
            entries[index] = newEntry
        } else {
            entries.add(newEntry)
        }

        dataSource.saveAllRecords(RecordsFile(entries))

        val newRequests = requestsFile.requests.filterNot { it.id == requestId }
        dataSource.saveAllRequests(RequestsFile(newRequests))
    }

    suspend fun adminRejectRequest(requestId: String) = withContext(Dispatchers.IO) {
        val requestsFile = dataSource.loadAllRequests()
        val req = requestsFile.requests.firstOrNull { it.id == requestId } ?: return@withContext

        dataSource.deleteRequestVideo(req.videoPath)

        val newRequests = requestsFile.requests.filterNot { it.id == requestId }
        dataSource.saveAllRequests(RequestsFile(newRequests))
    }

    private fun compareSubmissions(a: RecordSubmission, b: RecordSubmission): Int {
        val byPeso = a.peso.compareTo(b.peso)
        if (byPeso != 0) return byPeso
        return a.repeticiones.compareTo(b.repeticiones)
    }

    private fun isSameSubmission(a: RecordSubmission, b: RecordSubmission): Boolean {
        return a.usuarioId == b.usuarioId && a.peso == b.peso && a.repeticiones == b.repeticiones && a.fecha == b.fecha
    }
}