package com.example.gymtracker.data.records

import android.net.Uri
import com.example.gymtracker.model.RecordExerciseEntry
import com.example.gymtracker.model.RecordRequest
import com.example.gymtracker.model.RecordSubmission
import com.example.gymtracker.model.RecordsFile
import com.example.gymtracker.model.RequestsFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Repositorio que orquesta la lógica entre el data source local y la capa de UI.
 * - Guarda solicitudes con vídeo en la carpeta de requests
 * - Al aceptar una solicitud por el admin, mueve el vídeo a la carpeta de vídeos y actualiza el top3
 * - Solo se guarda el vídeo del primer puesto; si un primer puesto anterior cae a segundo/tercero su vídeo se elimina
 */
class RecordsRepository(private val dataSource: LocalRecordsDataSource) {

    suspend fun getTopsForExercise(ejercicioId: Int): List<RecordSubmission> = withContext(Dispatchers.IO) {
        val file = dataSource.loadAllRecords()
        val entry = file.exercises.firstOrNull { it.ejercicioId == ejercicioId }
        entry?.tops ?: emptyList()
    }

    suspend fun wouldEnterTop3(ejercicioId: Int, candidate: RecordSubmission): Boolean = withContext(Dispatchers.Default) {
        val file = dataSource.loadAllRecords()
        val entry = file.exercises.firstOrNull { it.ejercicioId == ejercicioId }
        val tops = entry?.tops ?: emptyList()
        if (tops.size < 3) return@withContext true
        // comparar con el peor de los tres (último)
        val worst = tops.sortedWith(compareByDescending<RecordSubmission> { it.peso }.thenByDescending { it.repeticiones }).take(3).last()
        return@withContext compareSubmissions(candidate, worst) > 0
    }

    suspend fun createRequest(ejercicioId: Int, submission: RecordSubmission, videoUri: Uri): String? = withContext(Dispatchers.IO) {
        // Guardar vídeo en carpeta de requests
        val savedPath = dataSource.saveRequestVideo(ejercicioId, submission.usuarioId, videoUri)
        if (savedPath == null) return@withContext null

        val id = UUID.randomUUID().toString()
        val req = RecordRequest(id = id, ejercicioId = ejercicioId, submission = submission.copy(videoPath = null), videoPath = savedPath)

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

        // mover vídeo de requests a vídeos
        val movedVideoPath = dataSource.moveRequestVideoToVideos(req.videoPath, req.ejercicioId, req.submission.usuarioId)

        // cargar registros actuales
        val recordsFile = dataSource.loadAllRecords()
        val entries = recordsFile.exercises.toMutableList()
        val index = entries.indexOfFirst { it.ejercicioId == req.ejercicioId }
        val existingEntry = if (index >= 0) entries[index] else null

        val mutableTops = existingEntry?.tops?.toMutableList() ?: mutableListOf()

        // construir la nueva submission; por defecto sin video, lo asignaremos solo al primero
        val newSubmission = req.submission.copy(videoPath = null)
        mutableTops.add(newSubmission)

        // ordenar y mantener top3
        val sorted = mutableTops.sortedWith(compareByDescending<RecordSubmission> { it.peso }.thenByDescending { it.repeticiones }).take(3).toMutableList()

        // gestionar vídeos: solo el primero debe tener vídeo almacenado
        // 1) Si el movedVideoPath es null -> no hay vídeo (fallo). En ese caso no asignamos vídeo y procedemos.
        // 2) Si la nueva submission quedó primera -> asignarle movedVideoPath
        // 3) Si no quedó primera -> borramos movedVideoPath (no interesa conservar)

        // identificar si la newSubmission is first by matching usuarioId/fecha/peso/repeticiones
        val first = sorted.firstOrNull()
        var newAssigned = false
        if (movedVideoPath != null && first != null && isSameSubmission(first, newSubmission)) {
            // asignar el vídeo al primer elemento (que es la nueva submission)
            sorted[0] = first.copy(videoPath = movedVideoPath)
            newAssigned = true
        }

        // si movedVideoPath exists but wasn't assigned to first, eliminar el fichero movido
        if (movedVideoPath != null && !newAssigned) {
            dataSource.deleteVideo(movedVideoPath)
        }

        // si había un antiguo primer puesto y ahora ya no es primero, borrar su vídeo
        val previousFirst = existingEntry?.tops?.firstOrNull()
        if (previousFirst != null) {
            // comprobar si previousFirst sigue siendo first en sorted; si no, borrar su vídeo
            val stillFirst = sorted.firstOrNull()?.let { isSameSubmission(it, previousFirst) } ?: false
            if (!stillFirst) {
                previousFirst.videoPath?.let { dataSource.deleteVideo(it) }
            }
        }

        // reconstruir/insertar entry
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

        // eliminar la solicitud del fichero de requests
        val newRequests = requestsFile.requests.filterNot { it.id == requestId }
        dataSource.saveAllRequests(RequestsFile(newRequests))
    }

    suspend fun adminRejectRequest(requestId: String) = withContext(Dispatchers.IO) {
        val requestsFile = dataSource.loadAllRequests()
        val req = requestsFile.requests.firstOrNull { it.id == requestId } ?: return@withContext

        // borrar vídeo de la carpeta de requests
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
