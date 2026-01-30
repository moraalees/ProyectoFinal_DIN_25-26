package com.example.gymtracker.data.repository

import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.model.Usuario

class UsuarioRepository(
    private val dsUsuario: UsuarioJsonDataSource
) {
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
