package com.example.gymtracker.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.R
import com.example.gymtracker.ui.theme.AzulOscuroFondo

@Composable
fun PantallaRegistro(
    viewModel: RegistroViewModel,
    registroExitoso: () -> Unit,
    pantallaLogin: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var nombreUsuario by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var mensajeError by rememberSaveable { mutableStateOf<String?>(null) }
    var visibilidadContrasena by rememberSaveable { mutableStateOf(false) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo del Gym",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp)
            )
            Text(
                text = "Crea tu cuenta",
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = nombre,
                onValueChange = {nombre = it},
                label = {
                    Text(
                        text = "Nombre completo",
                        color = Color.White
                    ) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = {nombreUsuario = it},
                label = {
                    Text(
                        text = "Nombre de usuario",
                        color = Color.White
                    ) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = correo,
                onValueChange = {correo = it},
                label = {
                    Text(
                        text = "Correo",
                        color = Color.White
                    ) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = {
                    Text(
                        text = "Contraseña", color = Color.White
                    ) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                ),
                visualTransformation =
                    if (visibilidadContrasena) {
                    VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    IconButton(
                        onClick = { visibilidadContrasena = !visibilidadContrasena }
                    ) {
                        val iconoCambioVisibilidad = if (visibilidadContrasena) {
                            android.R.drawable.ic_menu_view
                        } else {
                            android.R.drawable.ic_secure
                        }
                        Icon(
                            painter = painterResource(id = iconoCambioVisibilidad),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (mensajeError != null) {
                Text(
                    text = mensajeError!!,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Button(
                onClick = {
                    when {
                        nombre.isBlank() -> {
                            mensajeError = "Ingresa tu nombre completo"
                        }
                        nombreUsuario.length < 5 -> {
                            mensajeError = "El nombre de usuario debe tener al menos 5 caracteres"
                        }
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                            mensajeError = "Ingresa un correo válido"
                        }
                        contrasena.length < 8 -> {
                            mensajeError = "La contraseña debe tener al menos 8 caracteres"
                        }
                        else -> {
                            val error = viewModel.registrar(nombre, nombreUsuario, correo, contrasena)
                            if (error == null) {
                                mensajeError = null
                                registroExitoso()
                            } else {
                                mensajeError = error
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B3B5F),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = pantallaLogin
            ) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = Color(0xFF4DA8DA)
                )
            }
        }
    }
}
