package com.example.gymtracker.ui.screens.records

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymtracker.model.MarcaPersonal
import java.time.format.DateTimeFormatter
import com.example.gymtracker.data.repository.EjerciciosRepository

@Composable
fun FilaMarcaEjercicio(
    marca: MarcaPersonal,
    modifier: Modifier = Modifier
) {
    var mostrarDialog by remember { mutableStateOf(false) }
    var mostrarDialogVideo by remember { mutableStateOf(false) }
    var mostrarDialogNoOficial by remember { mutableStateOf(false) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val videoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            videoUri = uri
        }

    // comprobar si el ejercicio pertenece a la lista de ejercicios iniciales
    val ejercicioEsOficial = EjerciciosRepository.obtenerEjerciciosPrincipales()
        .any { it.id == marca.ejercicioId }

    val fecha = try {
        marca.fecha.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    } catch (_: Exception) {
        "Desconocida"
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { mostrarDialog = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = marca.nombreEjercicio,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${marca.pesoMaximo ?: "—"} kg · ${marca.repeticionesMaximas ?: "—"} rep",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = fecha,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
    if (mostrarDialog) {
        AlertDialog(
            onDismissRequest = { mostrarDialog = false },
            confirmButton = {
                TextButton(onClick = { mostrarDialog = false }) {
                    Text("Cerrar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialog = false
                        // sólo permitir subir vídeo si el ejercicio es oficial
                        if (ejercicioEsOficial) {
                            mostrarDialogVideo = true
                        } else {
                            mostrarDialogNoOficial = true
                        }
                    }
                ) {
                    Text("Subir marca")
                }
            },
            title = {
                Text(marca.nombreEjercicio)
            },
            text = {
                Column {
                    Text(
                        text = "Peso máximo: ${marca.pesoMaximo ?: "—"} kg"
                    )
                    Text(
                        text = "Repeticiones: ${marca.repeticionesMaximas ?: "—"}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Fecha: $fecha"
                    )
                }
            }
        )
    }
    if (mostrarDialogVideo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogVideo = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogVideo = false
                    },
                    enabled = videoUri != null
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogVideo = false }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text("Subir vídeo")
            },
            text = {
                Column {
                    Text("Debes seleccionar un vídeo como prueba de la marca")

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            videoPickerLauncher.launch("video/*")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (videoUri == null)
                                "Seleccionar vídeo"
                            else
                                "Vídeo seleccionado"
                        )
                    }

                    if (videoUri != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Vídeo listo para subir",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        )
    }

    if (mostrarDialogNoOficial) {
        AlertDialog(
            onDismissRequest = { mostrarDialogNoOficial = false },
            confirmButton = {
                TextButton(onClick = { mostrarDialogNoOficial = false }) {
                    Text("Aceptar")
                }
            },
            title = {
                Text("Ejercicio no oficial")
            },
            text = {
                Column {
                    Text("No puedes subir vídeo para este ejercicio porque no forma parte de la lista oficial de ejercicios.")
                }
            }
        )
    }

}
