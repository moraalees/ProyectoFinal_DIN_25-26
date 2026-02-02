package com.example.gymtracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.UsuarioGimnasio
import com.example.gymtracker.ui.navigation.Rutas
import com.example.gymtracker.ui.screens.routines.RutinasViewModel

/**
 * Composable que muestra un resumen visual de una rutina semanal.
 *
 * Muestra el nombre de la rutina y un indicador por día (círculo verde para día de entrenamiento,
 * rojo para día de descanso). Al hacer clic en la tarjeta, se abre un diálogo con acciones sobre la rutina:
 * establecer como activa, borrar, modificar o cancelar.
 *
 * @param plan [PlanSemanal] que representa la rutina a mostrar.
 * @param rutinaViewModel ViewModel encargado de manejar las acciones sobre la rutina.
 * @param perfil [UsuarioGimnasio] del usuario al que pertenece la rutina.
 * @param onModificar Lambda que se ejecuta cuando el usuario desea modificar la rutina.
 */
@Composable
fun RutinaSemanalComponente(
    plan: PlanSemanal,
    rutinaViewModel: RutinasViewModel,
    perfil: UsuarioGimnasio,
    onModificar: (PlanSemanal) -> Unit
) {
    val diasAbreviados = listOf("L", "M", "X", "J", "V", "S", "D")
    var mostrarDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp)
            .clickable { mostrarDialog = true },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = plan.nombre.replace("_", " ").split(" ")
                    .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                plan.dias.forEachIndexed { index, entreno ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = diasAbreviados.getOrElse(index) { "" },
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (entreno.esEntreno) Color(0xFF4CAF50) else Color(0xFFF44336))
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
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Acciones de la rutina",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            rutinaViewModel.asignarRutinaActiva(perfil, plan)
                            mostrarDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Establecer Activa", color = Color.White)
                    }

                    Button(
                        onClick = {
                            rutinaViewModel.borrarRutina(perfil, plan)
                            rutinaViewModel.cargarPerfil(perfil.usuarioId)
                            mostrarDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Borrar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            onModificar(plan)
                            mostrarDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Modificar", color = Color.Black)
                    }

                    Button(
                        onClick = { mostrarDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Cancelar", color = Color.Black)
                    }
                }
            }
        }
    }
}