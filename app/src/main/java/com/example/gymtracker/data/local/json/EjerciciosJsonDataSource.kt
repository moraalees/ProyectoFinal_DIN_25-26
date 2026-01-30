package com.example.gymtracker.data.local.json

import android.content.Context
import com.example.gymtracker.model.EjercicioBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException

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
}