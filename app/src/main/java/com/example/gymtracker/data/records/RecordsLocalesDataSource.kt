package com.example.gymtracker.data.records

import android.content.Context
import android.net.Uri
import com.example.gymtracker.model.RecordsFile
import com.example.gymtracker.model.RequestsFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocalRecordsDataSource(private val context: Context, private val gson: Gson = Gson()) {

    private val recordsDirName = "records"
    private val recordsFileName = "records.json"
    private val videosDirName = "videos"

    private val requestsFileName = "requests.json"
    private val requestsVideosDirName = "requests_videos"

    private fun recordsDir(): File = File(context.filesDir, recordsDirName).apply { if (!exists()) mkdirs() }
    private fun videosDir(): File = File(recordsDir(), videosDirName).apply { if (!exists()) mkdirs() }
    private fun recordsFile(): File = File(recordsDir(), recordsFileName)

    private fun requestsFile(): File = File(recordsDir(), requestsFileName)
    private fun requestsVideosDir(): File = File(recordsDir(), requestsVideosDirName).apply { if (!exists()) mkdirs() }

    suspend fun loadAllRecords(): RecordsFile = withContext(Dispatchers.IO) {
        try {
            val file = recordsFile()
            if (!file.exists()) return@withContext RecordsFile()

            val json = file.readText()
            val type = object : TypeToken<RecordsFile>() {}.type
            val data: RecordsFile = gson.fromJson(json, type)
            data
        } catch (e: Exception) {
            // en caso de error, devolver estructura vacía
            RecordsFile()
        }
    }

    suspend fun saveAllRecords(records: RecordsFile) = withContext(Dispatchers.IO) {
        val file = recordsFile()
        val json = gson.toJson(records)
        file.writeText(json)
    }

    suspend fun loadAllRequests(): RequestsFile = withContext(Dispatchers.IO) {
        try {
            val file = requestsFile()
            if (!file.exists()) return@withContext RequestsFile()
            val json = file.readText()
            val type = object : TypeToken<RequestsFile>() {}.type
            val data: RequestsFile = gson.fromJson(json, type)
            data
        } catch (e: Exception) {
            RequestsFile()
        }
    }

    suspend fun saveAllRequests(requests: RequestsFile) = withContext(Dispatchers.IO) {
        val file = requestsFile()
        val json = gson.toJson(requests)
        file.writeText(json)
    }

    /**
     * Copia el vídeo referenciado por sourceUri al storage interno y devuelve la ruta relativa (p. ej. "videos/52_167..._usuario.mp4").
     */
    suspend fun saveVideoForExercise(ejercicioId: Int, usuarioId: Int, sourceUri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val resolver = context.contentResolver
            val inputStream: InputStream? = resolver.openInputStream(sourceUri)
            if (inputStream == null) return@withContext null

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "${ejercicioId}_${timestamp}_${usuarioId}.mp4"
            val dest = File(videosDir(), fileName)

            val outputStream: OutputStream = dest.outputStream()

            inputStream.use { input ->
                outputStream.use { out ->
                    input.copyTo(out)
                }
            }

            // devolver ruta relativa
            return@withContext "${videosDirName}/${fileName}"
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    suspend fun saveRequestVideo(ejercicioId: Int, usuarioId: Int, sourceUri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val resolver = context.contentResolver
            val inputStream: InputStream? = resolver.openInputStream(sourceUri)
            if (inputStream == null) return@withContext null

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "req_${ejercicioId}_${timestamp}_${usuarioId}.mp4"
            val dest = File(requestsVideosDir(), fileName)

            val outputStream: OutputStream = dest.outputStream()

            inputStream.use { input ->
                outputStream.use { out ->
                    input.copyTo(out)
                }
            }

            return@withContext "${requestsVideosDirName}/${fileName}"
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    suspend fun deleteVideo(relativePath: String) = withContext(Dispatchers.IO) {
        try {
            val file = File(recordsDir(), relativePath)
            if (file.exists()) file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteRequestVideo(relativePath: String) = withContext(Dispatchers.IO) {
        try {
            val file = File(recordsDir(), relativePath)
            if (file.exists()) file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun moveRequestVideoToVideos(requestRelativePath: String, ejercicioId: Int, usuarioId: Int): String? = withContext(Dispatchers.IO) {
        try {
            val src = File(recordsDir(), requestRelativePath)
            if (!src.exists()) return@withContext null
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "${ejercicioId}_${timestamp}_${usuarioId}.mp4"
            val dest = File(videosDir(), fileName)
            val moved = src.renameTo(dest)
            return@withContext if (moved) "${videosDirName}/${fileName}" else null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}
