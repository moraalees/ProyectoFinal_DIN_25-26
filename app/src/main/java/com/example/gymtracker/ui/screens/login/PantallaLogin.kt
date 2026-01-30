package com.example.gymtracker.ui.screens.login

import androidx.compose.runtime.Composable
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
import com.example.gymtracker.ui.controllers.ControladorSesion

@Composable
fun PantallaLogin(
    loginExitoso: () -> Unit,
    pantallaRegistro: () -> Unit,
    viewModel: LoginViewModel,
    pantallaAdmin: () -> Unit
) {
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var mensajeError by rememberSaveable { mutableStateOf<String?>(null) }

    var visibilidadContrasena by rememberSaveable { mutableStateOf(false) }

    var saludoVisible by remember { mutableStateOf(false) }
    var nombreUsuarioSaludo by remember { mutableStateOf("") }
    var esAdminLocal by remember { mutableStateOf(false) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
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
                contentDescription = "Logo de la aplicación",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Bienvenido",
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text(text = "Correo", color = Color.White) },
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
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text(text = "Contraseña", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (visibilidadContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { visibilidadContrasena = !visibilidadContrasena }
                    ) {
                        val iconoCambioVisibilidad = if (visibilidadContrasena){
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
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
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
            if (saludoVisible) {
                Text(
                    text = "¡Hola, $nombreUsuarioSaludo!",
                    color = Color.Green,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (correo.isBlank() || contrasena.isBlank()) {
                        mensajeError = "Por favor completa todos los campos"
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        mensajeError = "Introduce un formato válido de correo"
                    } else if (contrasena.length < 8){
                        mensajeError = "La contraseña debe de tener al menos 8 caracteres."
                    } else {
                        val exito = viewModel.login(correo, contrasena)
                        if (exito) {
                            mensajeError = null
                            val usuario = ControladorSesion.usuarioLogueado()
                            nombreUsuarioSaludo = usuario?.nombreUsuario ?: "Usuario"
                            esAdminLocal = usuario?.esAdmin == true
                            saludoVisible = true

                        } else {
                            mensajeError = "Correo o contraseña incorrectos"
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
                Text(text = "Iniciar sesión", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = pantallaRegistro) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = Color(0xFF4DA8DA)
                )
            }
        }
    }

    if (saludoVisible) {
        LaunchedEffect(saludoVisible) {
            kotlinx.coroutines.delay(1000)
            saludoVisible = false
            if (esAdminLocal) {
                pantallaAdmin()
            } else {
                loginExitoso()
            }
        }
    }
}