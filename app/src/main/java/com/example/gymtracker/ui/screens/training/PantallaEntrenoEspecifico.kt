package com.example.gymtracker.ui.screens.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gymtracker.data.repository.EjerciciosRepository
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.ui.components.EjercicioComponent
import com.example.gymtracker.ui.components.EjercicioRutinaComponente

@Composable
fun PantallaEntrenoEspecifico(
    entreno: Entreno,
    rutinaId: String,
    usuario: Usuario,
    viewModel: EntrenoEspecificoViewModel,
    pantallaAnterior: () -> Unit
) {
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )

    var mostrarDialog by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(key1 = usuario.id) {
        EjerciciosRepository.inicializar(context, usuario.id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = if (entreno.esEntreno) "Día de Entreno" else "Día de Descanso",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            if (entreno.esEntreno && entreno.ejercicios.isNotEmpty()) {
                item {
                    Text(
                        text = "Ejercicios",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(
                    items = entreno.ejercicios,
                    key = { it.ejercicio.id }
                ) { ejercicio ->
                    EjercicioRutinaComponente(
                        ejercicioPlan = ejercicio,
                        onActualizarEjercicio = { actualizado ->
                            val entrenoActualizado = entreno.copy(
                                ejercicios = entreno.ejercicios.map {
                                    if (it.ejercicio.id == actualizado.ejercicio.id) actualizado else it
                                }.toMutableList()
                            )

                            viewModel.actualizarEntreno(
                                usuario = usuario,
                                rutinaId = rutinaId,
                                entrenoActualizado = entrenoActualizado
                            )
                        },
                        onBorrarEjercicio = { aBorrar ->
                            val entrenoActualizado = entreno.copy(
                                ejercicios = entreno.ejercicios.filter { it.ejercicio.id != aBorrar.ejercicio.id }.toMutableList(),
                                fecha = entreno.fecha
                            )

                            viewModel.actualizarEntreno(
                                usuario = usuario,
                                rutinaId = rutinaId,
                                entrenoActualizado = entrenoActualizado
                            )
                            pantallaAnterior()
                        }
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                }
            } else if (entreno.esEntreno) {
                item {
                    Text(
                        text = "Este entreno no tiene ejercicios asignados.",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                item {
                    Text(
                        text = "Este es un día de descanso.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Button(
            onClick = {
                val entrenoActualizado = entreno.copy(
                    esEntreno = !entreno.esEntreno,
                    ejercicios = mutableListOf()
                )

                viewModel.actualizarEntreno(
                    usuario = usuario,
                    rutinaId = rutinaId,
                    entrenoActualizado = entrenoActualizado
                )
                pantallaAnterior()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = if (entreno.esEntreno)
                    "Convertir en día de descanso"
                else
                    "Convertir en día de entreno"
            )
        }

        FloatingActionButton(
            onClick = { mostrarDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 90.dp),
            containerColor = Color(0xFF4CAF50)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir ejercicio",
                tint = Color.White
            )
        }
    }

    if (mostrarDialog && entreno.esEntreno) {
        Dialog(
            onDismissRequest = { mostrarDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1E1E1E)
            ) {
                var filtro by rememberSaveable { mutableStateOf("") }
                val ejercicios = EjerciciosRepository.obtenerTodosLosEjercicios()
                val ejerciciosFiltrados = ejercicios.filter {
                    it.nombre.contains(filtro, ignoreCase = true)
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Añadir ejercicio",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = filtro,
                        onValueChange = { filtro = it },
                        label = { Text("Buscar ejercicio") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF2A2A2A), RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.White
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ejerciciosFiltrados) { ejercicioBase ->
                            EjercicioComponent(
                                ejercicio = ejercicioBase,
                                onAgregar = { ejercicioPlan ->
                                    val entrenoActualizado = entreno.copy(
                                        ejercicios = (entreno.ejercicios + ejercicioPlan).toMutableList()
                                    )

                                    viewModel.actualizarEntreno(
                                        usuario = usuario,
                                        rutinaId = rutinaId,
                                        entrenoActualizado = entrenoActualizado
                                    )

                                    mostrarDialog = false
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { mostrarDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}
