package com.example.gymtracker.data.local.json

import android.content.Context
import java.io.FileNotFoundException

class GuardadoJson(
    private val contexto: Context
) {
    fun escribirArchivoJson(
        nombreArchivo: String,
        json: String
    ) {
        contexto.openFileOutput(nombreArchivo, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun leerArchivoJson(
        nombreArchivo: String
    ): String? {
        return try {
            contexto.openFileInput(nombreArchivo)
                .bufferedReader()
                .use { it.readText() }
        } catch (e: FileNotFoundException) {
            null
        }
    }
}
