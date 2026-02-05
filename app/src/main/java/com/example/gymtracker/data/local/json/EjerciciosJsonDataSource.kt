package com.example.gymtracker.data.local.json

import android.content.Context
import com.example.gymtracker.model.EjercicioBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException

/**
 * Fuente de datos para manejar ejercicios en formato JSON.
 *
 * Esta clase permite almacenar y recuperar una lista de ejercicios para un usuario específico
 * utilizando archivos locales dentro del contexto de la aplicación.
 *
 * Funciones principales:
 * - [obtenerEjercicios]: Obtiene la lista de ejercicios de un usuario desde un archivo JSON.
 *   Devuelve una lista vacía si no existe el archivo.
 * - [guardarEjercicios]: Guarda una lista de ejercicios de un usuario en un archivo JSON.
 *   Sobrescribe el archivo si ya existe.
 *
 * @property contexto Contexto de la aplicación, usado para acceder al sistema de archivos.
 * @property gson Instancia de Gson para serializar y deserializar objetos JSON.
 */
class EjerciciosJsonDataSource(
    private val contexto: Context,
    private val gson: Gson
) {
    private fun nombreArchivo(userId: String) = "ejercicios_$userId.json"

    fun obtenerEjercicios(usuarioId: String): List<EjercicioBase> {
        val archivo = nombreArchivo(usuarioId)
        val json = try {
            contexto.openFileInput(archivo).bufferedReader().use { it.readText() }
        } catch (e: FileNotFoundException) {
            return emptyList()
        }
        return gson.fromJson(json, object : TypeToken<List<EjercicioBase>>() {}.type)
    }

    fun guardarEjercicios(usuarioId: String, ejercicios: List<EjercicioBase>) {
        val json = gson.toJson(ejercicios)
        contexto.openFileOutput(nombreArchivo(usuarioId), Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    /**
    fun fusionarEjercicios(usuarioId: String, nuevos: List<EjercicioBase>) {
        val actuales = obtenerEjercicios(usuarioId).toMutableList()
        val existentes = actuales.map { it.id }.toSet()

        nuevos.filter { it.id !in existentes }
            .forEach { actuales.add(it) }

        guardarEjercicios(usuarioId, actuales)
    }
     Este bloque se usó para pruebas
        */
}