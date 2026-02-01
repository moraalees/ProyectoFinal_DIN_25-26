package com.example.gymtracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.EjercicioPlan
import com.example.gymtracker.ui.theme.RojoError

@Composable
fun EjercicioRutinaComponente(
    ejercicioPlan: EjercicioPlan,
    onActualizarEjercicio: (EjercicioPlan) -> Unit,
    onBorrarEjercicio: (EjercicioPlan) -> Unit
) {
    var mostrarDialog by rememberSaveable { mutableStateOf(false) }
    var series by rememberSaveable { mutableStateOf(ejercicioPlan.series.toString()) }
    var repInicio by rememberSaveable { mutableStateOf(ejercicioPlan.repeticiones.first.toString()) }
    var repFin by rememberSaveable { mutableStateOf(ejercicioPlan.repeticiones.last.toString()) }
    var peso by rememberSaveable { mutableStateOf(ejercicioPlan.pesoEstimado?.toString() ?: "0") }
    val pesoTexto = ejercicioPlan.pesoEstimado?.toString() ?: "0"

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable { mostrarDialog = true }
            .height(180.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = ejercicioPlan.ejercicio.imagen),
                    contentDescription = "Ejercicio de la rutina",
                    modifier = Modifier
                        .size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = ejercicioPlan.ejercicio.nombre,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Series: ${ejercicioPlan.series}",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Reps: ${ejercicioPlan.repeticiones.first} - ${ejercicioPlan.repeticiones.last}",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Peso estimado: $pesoTexto kg",
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }

    if (mostrarDialog) {
        AlertDialog(
            onDismissRequest = { mostrarDialog = false },
            title = { Text(text = "Editar ${ejercicioPlan.ejercicio.nombre}") },
            text = {
                Column {
                    OutlinedTextField(
                        value = series,
                        onValueChange = { series = it.filter { c -> c.isDigit() } },
                        label = { Text("Series") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = repInicio,
                        onValueChange = { repInicio = it.filter { c -> c.isDigit() } },
                        label = { Text("Repeticiones inicio") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = repFin,
                        onValueChange = { repFin = it.filter { c -> c.isDigit() } },
                        label = { Text("Repeticiones fin") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = peso,
                        onValueChange = { peso = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Peso estimado (kg)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        ejercicioPlan.series = series.toIntOrNull() ?: ejercicioPlan.series
                        ejercicioPlan.repeticiones =
                            (repInicio.toIntOrNull() ?: ejercicioPlan.repeticiones.first) ..
                                    (repFin.toIntOrNull() ?: ejercicioPlan.repeticiones.last)
                        ejercicioPlan.pesoEstimado = peso.toDoubleOrNull() ?: ejercicioPlan.pesoEstimado

                        onActualizarEjercicio(ejercicioPlan)
                        mostrarDialog = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Row {
                    TextButton(onClick = { mostrarDialog = false }) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(
                        onClick = {
                            onBorrarEjercicio(ejercicioPlan)
                            mostrarDialog = false
                        }
                    ) {
                        Text("Borrar", color = RojoError)
                    }
                }
            }
        )
    }
}