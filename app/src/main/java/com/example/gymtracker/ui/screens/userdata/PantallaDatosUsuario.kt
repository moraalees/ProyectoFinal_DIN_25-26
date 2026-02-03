package com.example.gymtracker.ui.screens.userdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.enum_classes.Altura
import com.example.gymtracker.model.enum_classes.Edad
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.model.enum_classes.ModoDatos
import com.example.gymtracker.ui.components.CampoEditable
import com.example.gymtracker.ui.components.EnumDropdown
import com.example.gymtracker.ui.theme.AzulOscuroFondo
import com.example.gymtracker.ui.theme.RojoError

/**
 * Composable que muestra y permite editar los datos del usuario y su perfil de gimnasio.
 *
 * Dependiendo del modo seleccionado, se muestran:
 * - Modo USUARIO: campos para editar nombre, nombre de usuario, correo y contraseña.
 * - Modo GIMNASIO: campos para editar edad, altura, peso, experiencia y enfoque del perfil de gimnasio.
 *
 * @param usuario Usuario actual cuyos datos se van a mostrar y editar.
 * @param volverHome Lambda que se ejecuta para volver a la pantalla principal.
 * @param viewModel ViewModel que maneja la lógica de actualización de datos y perfil de gimnasio.
 */
@Composable
fun PantallaDatosUsuario(
    usuario: Usuario,
    volverHome: () -> Unit,
    viewModel: DatosViewModel
) {
    var modo by rememberSaveable { mutableStateOf(ModoDatos.USUARIO) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    LaunchedEffect(Unit) {
        viewModel.cargarDatos(usuario)
    }

    val usuarioState = viewModel.usuario
    val perfil = viewModel.perfilGimnasio

    var pesoTexto by rememberSaveable { mutableStateOf(perfil?.peso?.toString() ?: "") }
    var pesoError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = if (modo == ModoDatos.USUARIO) "Datos de Usuario" else "Datos de Gimnasio",
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 30.dp, bottom = 40.dp)
        )

        Spacer(Modifier.height(16.dp))

        if (modo == ModoDatos.USUARIO && usuarioState != null) {
            CampoEditable(
                label = "Nombre",
                valorActual = usuarioState.nombre,
                validadorErrores = { if (it.isBlank()) "Ingresa tu nombre completo" else null }
            ) { viewModel.cambiarNombre(it) }

            CampoEditable(
                label = "Nombre de usuario",
                valorActual = usuarioState.nombreUsuario,
                validadorErrores = { if (it.length < 5) "Debe tener al menos 5 caracteres" else null }
            ) { viewModel.cambiarNombreUsuario(it) }

            CampoEditable(
                label = "Correo",
                valorActual = usuarioState.correo,
                validadorErrores = {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Correo inválido"
                    else null
                }
            ) { viewModel.cambiarCorreo(it) }

            CampoEditable(
                label = "Contraseña",
                valorActual = "********",
                validadorErrores = { if (it.length < 8) "Mínimo 8 caracteres" else null }
            ) { viewModel.cambiarContrasena(it) }
        }

        if (modo == ModoDatos.GIMNASIO && perfil != null && !usuario.esAdmin) {

            Text("Edad", color = Color.White, fontSize = 14.sp, fontStyle = FontStyle.Italic)
            EnumDropdown(
                etiqueta = "Edad",
                seleccionado = perfil.edad,
                valoresEnum = Edad.entries
            ) {
                viewModel.guardarPerfilGimnasio(perfil.copy(edad = it))
            }

            Text("Altura", color = Color.White, fontSize = 14.sp, fontStyle = FontStyle.Italic)
            EnumDropdown(
                etiqueta = "Altura",
                seleccionado = perfil.altura,
                valoresEnum = Altura.entries
            ) {
                viewModel.guardarPerfilGimnasio(perfil.copy(altura = it))
            }

            Text("Peso", color = Color.White, fontSize = 14.sp, fontStyle = FontStyle.Italic)

            var pesoTemporal by rememberSaveable { mutableStateOf(perfil.peso?.toString() ?: "50") }
            var pesoError by remember { mutableStateOf<String?>(null) }

            Column(horizontalAlignment = Alignment.Start) {
                OutlinedTextField(
                    value = pesoTemporal,
                    onValueChange = { nuevoValor ->
                        pesoTemporal = nuevoValor
                        val pesoDouble = nuevoValor.toDoubleOrNull()
                        pesoError = when {
                            pesoDouble == null -> "Debe ser un número válido"
                            pesoDouble < 20 -> "El peso mínimo es 20 kg"
                            pesoDouble > 330 -> "El peso máximo es 330 kg"
                            else -> null
                        }
                    },
                    label = { Text("Peso (kg)", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(0.4f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    isError = pesoError != null
                )

                if (pesoError != null) {
                    Text(
                        text = pesoError!!,
                        color = RojoError,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }

                Spacer(Modifier.height(4.dp))

                Button(
                    onClick = {
                        val pesoDouble = pesoTemporal.toDoubleOrNull()
                        if (pesoDouble != null && pesoError == null) {
                            viewModel.guardarPerfilGimnasio(perfil.copy(peso = pesoDouble))
                        } else {
                            pesoTemporal = perfil.peso?.toString() ?: "50"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.3f)
                ) {
                    Text("Aceptar")
                }
            }

            Text("Experiencia", color = Color.White, fontSize = 14.sp, fontStyle = FontStyle.Italic)
            EnumDropdown(
                etiqueta = "Experiencia",
                seleccionado = perfil.experiencia,
                valoresEnum = Experiencia.entries
            ) {
                viewModel.guardarPerfilGimnasio(perfil.copy(experiencia = it))
            }

            Text("Enfoque", color = Color.White, fontSize = 14.sp, fontStyle = FontStyle.Italic)
            EnumDropdown(
                etiqueta = "Enfoque",
                seleccionado = perfil.enfoque,
                valoresEnum = Enfoque.entries
            ) {
                viewModel.guardarPerfilGimnasio(perfil.copy(enfoque = it))
            }
        }


        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val pesoDouble = pesoTexto.toDoubleOrNull()
                if (pesoDouble != null && perfil != null && pesoError == null) {
                    viewModel.guardarPerfilGimnasio(
                        perfil.copy(
                            peso = pesoDouble,
                            edad = perfil.edad,
                            altura = perfil.altura,
                            experiencia = perfil.experiencia,
                            enfoque = perfil.enfoque
                        )
                    )
                }

                modo = if (modo == ModoDatos.USUARIO) ModoDatos.GIMNASIO else ModoDatos.USUARIO
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                if (modo == ModoDatos.USUARIO)
                    "Mostrar datos de gimnasio"
                else
                    "Mostrar datos de usuario"
            )
        }

        TextButton(onClick = volverHome) {
            Text("Volver")
        }
    }
}


