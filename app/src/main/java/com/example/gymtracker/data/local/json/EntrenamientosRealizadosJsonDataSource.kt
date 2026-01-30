package com.example.gymtracker.data.local.json

import android.content.Context
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.model.PlanSemanal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException

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