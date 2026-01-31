package com.example.gymtracker.data.records

import android.content.Context
import android.net.Uri
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

    private fun recordsDir(): File = File(context.filesDir, recordsDirName).apply { if (!exists()) mkdirs() }
    private fun videosDir(): File = File(recordsDir(), videosDirName).apply { if (!exists()) mkdirs() }
    private fun recordsFile(): File = File(recordsDir(), recordsFileName)

    suspend fun loadAllRecords(): RecordsFile = withContext(Dispatchers.IO) {
        try {
            val file = recordsFile()
            if (!file.exists()) return@withContext RecordsFile()

            val json = file.readText()
            val type = object : TypeToken<RecordsFile>() {}.type
            val data: RecordsFile = gson.fromJson(json, type)
            data
        } catch (e: Exception) {
            RecordsFile()
        }
    }

    suspend fun saveAllRecords(records: RecordsFile) = withContext(Dispatchers.IO) {
        val file = recordsFile()
        val json = gson.toJson(records)
        file.writeText(json)
    }

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

    suspend fun deleteVideo(relativePath: String) = withContext(Dispatchers.IO) {
        try {
            val file = File(recordsDir(), relativePath)
            if (file.exists()) file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
