package com.example.gymtracker.data.repository

import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.model.Usuario

/**
 * Repositorio para manejar la gestión de usuarios.
 *
 * Este repositorio permite:
 * - Obtener un usuario por su ID.
 * - Registrar un nuevo usuario.
 * - Iniciar sesión validando credenciales.
 * - Actualizar datos del usuario como nombre, nombre de usuario, correo y contraseña.
 *
 * Funciones principales:
 * - [obtenerUsuarioPorId]: Devuelve un usuario según su ID.
 * - [registrarUsuario]: Registra un nuevo usuario si el correo no está en uso.
 * - [iniciarSesion]: Valida credenciales y devuelve el usuario si son correctas.
 * - [cambiarNombre], [cambiarNombreUsuario], [cambiarCorreo], [cambiarContrasena]: Actualizan los datos del usuario.
 */
class UsuarioRepository(
    private val dsUsuario: UsuarioJsonDataSource
) {
    fun obtenerUsuarioPorId(id: Int): Usuario? {
        return dsUsuario.obtenerUsuarios().find { it.id == id }
    }

    fun registrarUsuario(
        nombre: String,
        nombreUsuario: String,
        correo: String,
        contrasena: String
    ): Result<Usuario> {
        val usuarios = dsUsuario.obtenerUsuarios().toMutableList()

        if (usuarios.any { it.correo == correo }) {
            return Result.failure(Exception("Correo ya registrado"))
        }

        val nuevoUsuario = Usuario(
            id = usuarios.size + 1,
            nombre = nombre,
            nombreUsuario = nombreUsuario,
            correo = correo,
            contrasena = contrasena
        )
        usuarios.add(nuevoUsuario)
        dsUsuario.guardarUsuarios(usuarios)

        return Result.success(nuevoUsuario)
    }

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ): Result<Usuario> {
        val usuario = dsUsuario.obtenerUsuarios()
            .find { it.correo == correo && it.contrasena == contrasena }

        return usuario?.let {
            Result.success(it)
        } ?: Result.failure(Exception("Credenciales incorrectas"))
    }

    fun cambiarNombre(nuevoNombre: String, usuario: Usuario): Result<Usuario> {
        val usuarios = dsUsuario.obtenerUsuarios().toMutableList()
        val index = usuarios.indexOfFirst { it.id == usuario.id }

        if (index == -1) {
            return Result.failure(Exception("Usuario no encontrado"))
        }

        val usuarioActualizado = usuarios[index].copy(nombre = nuevoNombre)
        usuarios[index] = usuarioActualizado
        dsUsuario.guardarUsuarios(usuarios)

        return Result.success(usuarioActualizado)
    }

    fun cambiarNombreUsuario(nuevoNombreUsuario: String, usuario: Usuario): Result<Usuario> {
        val usuarios = dsUsuario.obtenerUsuarios().toMutableList()

        if (usuarios.any { it.nombreUsuario == nuevoNombreUsuario }) {
            return Result.failure(Exception("Nombre de usuario ya en uso"))
        }

        val index = usuarios.indexOfFirst { it.id == usuario.id }
        if (index == -1) {
            return Result.failure(Exception("Usuario no encontrado"))
        }

        val usuarioActualizado = usuarios[index].copy(nombreUsuario = nuevoNombreUsuario)
        usuarios[index] = usuarioActualizado
        dsUsuario.guardarUsuarios(usuarios)

        return Result.success(usuarioActualizado)
    }

    fun cambiarCorreo(nuevoCorreo: String, usuario: Usuario): Result<Usuario> {
        val usuarios = dsUsuario.obtenerUsuarios().toMutableList()

        if (usuarios.any { it.correo == nuevoCorreo }) {
            return Result.failure(Exception("Correo ya registrado"))
        }

        val index = usuarios.indexOfFirst { it.id == usuario.id }
        if (index == -1) {
            return Result.failure(Exception("Usuario no encontrado"))
        }

        val usuarioActualizado = usuarios[index].copy(correo = nuevoCorreo)
        usuarios[index] = usuarioActualizado
        dsUsuario.guardarUsuarios(usuarios)

        return Result.success(usuarioActualizado)
    }

    fun cambiarContrasena(nuevaContrasena: String, usuario: Usuario): Result<Usuario> {
        val usuarios = dsUsuario.obtenerUsuarios().toMutableList()
        val index = usuarios.indexOfFirst { it.id == usuario.id }

        if (index == -1) {
            return Result.failure(Exception("Usuario no encontrado"))
        }

        val usuarioActualizado = usuarios[index].copy(contrasena = nuevaContrasena)
        usuarios[index] = usuarioActualizado
        dsUsuario.guardarUsuarios(usuarios)

        return Result.success(usuarioActualizado)
    }
}
