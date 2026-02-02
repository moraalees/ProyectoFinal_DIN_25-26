package com.example.gymtracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gymtracker.model.EjercicioBase
import com.example.gymtracker.model.EjercicioPlan

/**
 * Composable que muestra la información de un ejercicio y permite al usuario:
 * - Ver detalles como nombre, descripción, tipo de peso y músculos trabajados.
 * - Abrir un diálogo para configurar series, repeticiones y peso estimado.
 * - Añadir el ejercicio configurado a una rutina mediante un callback opcional.
 *
 * Muestra un Card principal con la imagen y nombre del ejercicio. Al hacer clic se abre un
 * diálogo con la descripción completa y opciones de cierre o añadir a rutina. Si se elige
 * añadir a rutina, se abre un segundo diálogo para configurar parámetros antes de confirmar.
 *
 * @param ejercicio El ejercicio base que se muestra.
 * @param onAgregar Callback opcional que se ejecuta al confirmar la adición del ejercicio
 *                 configurado a una rutina. Recibe un objeto `EjercicioPlan`.
 */
@Composable
fun EjercicioComponent(
    ejercicio: EjercicioBase,
    onAgregar: ((EjercicioPlan) -> Unit)? = null
) {
    var mostrarDialog by rememberSaveable { mutableStateOf(false) }
    var mostrarDialogConfig by rememberSaveable { mutableStateOf(false) }

    var series by rememberSaveable { mutableStateOf("3") }
    var repMin by rememberSaveable { mutableStateOf("10") }
    var repMax by rememberSaveable { mutableStateOf("12") }
    var peso by rememberSaveable { mutableStateOf("30") }

    Card(
        modifier = Modifier
            .height(280.dp)
            .width(200.dp)
            .clickable { mostrarDialog = true }
            .padding(12.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = ejercicio.imagen),
                contentDescription = "Imagen del ejercicio",
                modifier = Modifier.size(110.dp)
            )
            Text(
                text = ejercicio.nombre,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ejercicio.musculos.forEach { musculo ->
                    Box(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = musculo.toString()
                                .lowercase()
                                .replace("_", " ")
                                .replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    if (mostrarDialog) {
        Dialog(onDismissRequest = { mostrarDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ejercicio.nombre,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ID: ${ejercicio.id}",
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        text = ejercicio.descripcion,
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Image(
                        painter = painterResource(id = ejercicio.imagen),
                        contentDescription = "Imagen del ejercicio",
                        modifier = Modifier
                            .size(130.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Row {
                        Text(
                            text = "Tipo Peso: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = ejercicio.tipoPeso.toString()
                                .lowercase()
                                .replace("_", " ")
                                .replaceFirstChar { it.uppercase() }
                        )
                    }

                    Column {
                        Text(
                            text = "Músculos:",
                            fontWeight = FontWeight.Bold
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ejercicio.musculos.forEach { musculo ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = musculo.toString()
                                            .lowercase()
                                            .replace("_", " ")
                                            .split(" ")
                                            .joinToString(" ") {
                                                it.replaceFirstChar { c -> c.uppercase() }
                                            },
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }

                    Button(
                        onClick = { mostrarDialog = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cerrar")
                    }

                    if (onAgregar != null) {
                        Button(
                            onClick = {
                                mostrarDialogConfig = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Añadir a rutina")
                        }
                    }

                }
            }
        }
    }

    if (mostrarDialogConfig) {
        Dialog(onDismissRequest = { mostrarDialogConfig = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Configurar ejercicio",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    OutlinedTextField(
                        value = series,
                        onValueChange = { series = it },
                        label = { Text("Series") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = repMin,
                            onValueChange = { repMin = it },
                            label = { Text("Reps mín") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = repMax,
                            onValueChange = { repMax = it },
                            label = { Text("Reps máx") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = peso,
                        onValueChange = { peso = it },
                        label = { Text("Peso estimado (kg)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    Spacer(Modifier.height(8.dp))

                    val seriesInt = series.toIntOrNull()
                    val repMinInt = repMin.toIntOrNull()
                    val repMaxInt = repMax.toIntOrNull()
                    val pesoDouble = peso.toDoubleOrNull()

                    val formularioValido = seriesInt != null && seriesInt > 0 &&
                                repMinInt != null && repMinInt > 0 &&
                                repMaxInt != null && repMaxInt >= repMinInt &&
                                pesoDouble != null && pesoDouble >= 0

                    Button(
                        onClick = {
                            val ejercicioPlan = EjercicioPlan(
                                ejercicio = ejercicio,
                                series = seriesInt!!,
                                repeticiones = repMinInt!!..repMaxInt!!,
                                pesoEstimado = pesoDouble!!
                            )

                            onAgregar?.invoke(ejercicioPlan)
                            mostrarDialogConfig = false
                            mostrarDialog = false
                        },
                        enabled = formularioValido,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}
