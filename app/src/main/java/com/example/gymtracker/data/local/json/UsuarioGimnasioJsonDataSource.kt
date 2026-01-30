package com.example.gymtracker.data.local.json

import com.example.gymtracker.model.UsuarioGimnasio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

