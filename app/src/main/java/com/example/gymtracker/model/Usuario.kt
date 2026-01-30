package com.example.gymtracker.model

data class Usuario(
    val id: Int,
    val nombre: String,
    var nombreUsuario: String,
    var contrasena: String,
    var correo: String,
    val esAdmin: Boolean = false
)