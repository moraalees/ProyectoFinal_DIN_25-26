package com.example.gymtracker.data.local.json

import android.content.Context
import com.example.gymtracker.model.SesionPersistida
import com.google.gson.Gson
import java.io.FileNotFoundException

/**
* Administrador de sesión persistida en formato JSON.
*
* Este objeto permite guardar, cargar y borrar la sesión actual de un usuario
* utilizando un archivo local llamado `sesion.json` en el almacenamiento interno de la aplicación.
*
* Funciones principales:
* - [guardar]: Guarda una sesión de usuario en el archivo `sesion.json`.
* - [cargar]: Carga la sesión guardada desde el archivo `sesion.json`. Devuelve `null` si no existe.
* - [borrarSesion]: Borra la sesión actual, reemplazándola por una sesión vacía con `usuarioActualId = null`.
*/
object SesionJsonManager {
    private const val NOMBRE_ARCHIVO = "sesion.json"
    private val gson = Gson()

    fun guardar(context: Context, sesion: SesionPersistida) {
        val json = gson.toJson(sesion)
        context.openFileOutput(NOMBRE_ARCHIVO, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun cargar(context: Context): SesionPersistida? {
        val file = try {
            context.openFileInput(NOMBRE_ARCHIVO)
        } catch (e: FileNotFoundException) {
            return null
        }
        val json = file.bufferedReader().use { it.readText() }
        return gson.fromJson(json, SesionPersistida::class.java)
    }

    fun borrarSesion(context: Context) {
        guardar(context, SesionPersistida(usuarioActualId = null))
    }
}
