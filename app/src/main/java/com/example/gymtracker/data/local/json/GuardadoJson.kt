package com.example.gymtracker.data.local.json

import android.content.Context
import java.io.FileNotFoundException

/**
 * Utilidad para leer y escribir archivos JSON en el almacenamiento interno de la aplicación.
 *
 * Esta clase permite guardar cadenas JSON en archivos locales y leerlas posteriormente.
 *
 * Funciones principales:
 * - [escribirArchivoJson]: Guarda una cadena JSON en un archivo. Sobrescribe el archivo si ya existe.
 * - [leerArchivoJson]: Lee el contenido de un archivo JSON. Devuelve `null` si el archivo no existe.
 *
 * @property contexto Contexto de la aplicación, usado para acceder al sistema de archivos.
 */
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
