package com.example.gymtracker.data.local.json

import android.content.Context
import com.example.gymtracker.model.Entreno
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException

/**
 * Fuente de datos para manejar entrenamientos realizados en formato JSON.
 *
 * Esta clase permite almacenar y recuperar entrenamientos de un usuario específico
 * utilizando archivos locales dentro del contexto de la aplicación.
 *
 * Funciones principales:
 * - [obtenerEntrenamientos]: Obtiene la lista de entrenamientos de un usuario desde un archivo JSON.
 *   Devuelve una lista vacía si no existe el archivo.
 * - [guardarEntrenamiento]: Guarda un entrenamiento de un usuario en un archivo JSON.
 *   Añade el nuevo entrenamiento a los existentes y sobrescribe el archivo.
 *
 * @property contexto Contexto de la aplicación, usado para acceder al sistema de archivos.
 * @property gson Instancia de Gson para serializar y deserializar objetos JSON.
 */
class EntrenamientosRealizadosJsonDataSource(
    private val contexto: Context,
    private val gson: Gson
) {

    private fun nombreArchivo(userId: Int) = "entrenamientos_$userId.json"

    fun obtenerEntrenamientos(usuarioId: Int): List<Entreno> {
        val archivo = nombreArchivo(usuarioId)
        val json = try {
            contexto.openFileInput(archivo).bufferedReader().use { it.readText() }
        } catch (e: FileNotFoundException) {
            return emptyList()
        }

        return gson.fromJson(json, object : TypeToken<List<Entreno>>() {}.type)
    }

    fun guardarEntrenamiento(usuarioId: Int, entreno: Entreno) {
        val actuales = obtenerEntrenamientos(usuarioId).toMutableList()
        actuales.add(entreno)

        val json = gson.toJson(actuales)
        contexto.openFileOutput(nombreArchivo(usuarioId), Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }
}