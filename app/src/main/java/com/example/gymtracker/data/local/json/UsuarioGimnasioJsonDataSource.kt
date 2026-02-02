package com.example.gymtracker.data.local.json

import com.example.gymtracker.model.UsuarioGimnasio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Fuente de datos para manejar los perfiles de usuarios de gimnasio en formato JSON.
 *
 * Esta clase permite almacenar y recuperar la lista de usuarios de gimnasio
 * utilizando un archivo local llamado `usuarios_gimnasio.json`.
 *
 * Funciones principales:
 * - [obtenerPerfiles]: Obtiene la lista de todos los perfiles guardados. Devuelve una lista vacía si no hay datos.
 * - [guardarPerfiles]: Guarda o actualiza la lista de perfiles en el archivo JSON.
 *
 * @property guardado Instancia de [GuardadoJson] usada para leer y escribir archivos JSON.
 * @property gson Instancia de Gson para serializar y deserializar objetos JSON.
 */
class UsuarioGimnasioJsonDataSource(
    private val guardado: GuardadoJson,
    private val gson: Gson
) {
    private val nombreArchivo = "usuarios_gimnasio.json"

    fun obtenerPerfiles(): List<UsuarioGimnasio> {
        val json = guardado.leerArchivoJson(nombreArchivo) ?: return emptyList()
        return gson.fromJson(
            json,
            object : TypeToken<List<UsuarioGimnasio>>() {}.type
        )
    }

    fun guardarPerfiles(perfiles: List<UsuarioGimnasio>) {
        guardado.escribirArchivoJson(nombreArchivo, gson.toJson(perfiles))
    }
}

