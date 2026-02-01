package com.example.gymtracker.ui.screens.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.enum_classes.Altura
import com.example.gymtracker.model.enum_classes.Edad
import com.example.gymtracker.model.enum_classes.Enfoque
import com.example.gymtracker.model.enum_classes.Experiencia
import com.example.gymtracker.ui.components.EnumDropdown
import com.example.gymtracker.ui.theme.AzulOscuroFondo

@Composable
fun PantallaFormulario(
    onGuardar: (
        Experiencia,
        Enfoque,
        Edad,
        Altura,
        Double
    ) -> Unit
) {
    var experiencia by rememberSaveable { mutableStateOf<Experiencia?>(null) }
    var enfoque by rememberSaveable { mutableStateOf<Enfoque?>(null) }
    var edad by rememberSaveable { mutableStateOf<Edad?>(null) }
    var altura by rememberSaveable { mutableStateOf<Altura?>(null) }
    var peso by rememberSaveable { mutableStateOf("") }
    var pesoError by rememberSaveable { mutableStateOf<String?>(null) }

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
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Tu perfil de gimnasio",
                fontSize = 26.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            EnumDropdown(
                etiqueta = "Experiencia",
                seleccionado = experiencia,
                valoresEnum = Experiencia.entries,
                seleccion = { experiencia = it }
            )

            EnumDropdown(
                etiqueta = "Enfoque",
                seleccionado = enfoque,
                valoresEnum = Enfoque.entries,
                seleccion = { enfoque = it }
            )

            EnumDropdown(
                etiqueta = "Edad",
                seleccionado = edad,
                valoresEnum = Edad.entries,
                seleccion = { edad = it }
            )

            EnumDropdown(
                etiqueta = "Altura",
                seleccionado = altura,
                valoresEnum = Altura.entries,
                seleccion = { altura = it }
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { nuevoValor ->
                    peso = nuevoValor
                    val pesoDouble = nuevoValor.toDoubleOrNull()
                    pesoError = when {
                        pesoDouble == null -> "Debe ser un número válido"
                        pesoDouble < 20 -> "El peso mínimo es 20 kg"
                        pesoDouble > 330 -> "El peso máximo es 330 kg"
                        else -> null
                    }
                },
                label = { Text("Peso (kg)", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
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
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                )
            }

            val pesoDouble = peso.toDoubleOrNull()
            val pesoValido = pesoDouble != null && pesoError == null

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onGuardar(
                        experiencia!!,
                        enfoque!!,
                        edad!!,
                        altura!!,
                        pesoDouble!!
                    )
                },
                enabled =
                    experiencia != null &&
                            enfoque != null &&
                            edad != null &&
                            altura != null &&
                            pesoValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B3B5F),
                    contentColor = Color.White
                )
            ) {
                Text("Guardar perfil", fontSize = 16.sp)
            }
        }
    }
}
