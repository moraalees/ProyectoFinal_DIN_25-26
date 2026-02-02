package com.example.gymtracker.data.local.json

import com.example.gymtracker.model.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Fuente de datos para manejar usuarios en formato JSON.
 *
 * Esta clase permite almacenar y recuperar la lista de usuarios de la aplicación
 * utilizando un archivo local llamado `usuarios.json`.
 * Si no existen usuarios guardados, se crea automáticamente un usuario administrador por defecto.
 *
 * Funciones principales:
 * - [obtenerUsuarios]: Obtiene la lista de usuarios guardados. Si no hay usuarios, crea un administrador por defecto.
 * - [guardarUsuarios]: Guarda o actualiza la lista de usuarios en el archivo JSON.
 *
 * @property guardado Instancia de [GuardadoJson] usada para leer y escribir archivos JSON.
 * @property gson Instancia de Gson para serializar y deserializar objetos JSON.
 */
class UsuarioJsonDataSource(
    private val guardado: GuardadoJson,
    private val gson: Gson
) {
    private val nombreArchivoJson = "usuarios.json"

    fun obtenerUsuarios(): List<Usuario> {
        val json = guardado.leerArchivoJson(nombreArchivoJson)
            ?: return crearAdminPorDefecto().also { guardarUsuarios(it) }

        val usuarios: List<Usuario> = gson.fromJson(
            json,
            object : TypeToken<List<Usuario>>() {}.type
        )

        if (usuarios.isEmpty()) {
            val adminList = crearAdminPorDefecto()
            guardarUsuarios(adminList)
            return adminList
        }

        return usuarios
    }

    private fun crearAdminPorDefecto(): List<Usuario> {
        val admin = Usuario(
            id = 1,
            nombre = "Administrador",
            nombreUsuario = "admin",
            correo = "admin@admin.com",
            contrasena = "admin123",
            esAdmin = true
        )

        return listOf(admin)
    }

    fun guardarUsuarios(usuarios: List<Usuario>) {
        val json = gson.toJson(usuarios)
        guardado.escribirArchivoJson(nombreArchivoJson, json)
    }
}
