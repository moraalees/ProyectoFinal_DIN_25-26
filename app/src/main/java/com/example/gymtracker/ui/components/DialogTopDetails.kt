package com.example.gymtracker.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymtracker.model.RecordExerciseEntry
import com.example.gymtracker.model.RecordSubmission
import java.time.LocalDate

/**
 * Composable que muestra un diálogo con los detalles del top de un ejercicio y permite al usuario
 * enviar su propia marca personal con un vídeo asociado.
 *
 * Muestra los tres mejores registros del ejercicio, permite ingresar peso y repeticiones,
 * seleccionar un vídeo y comprobar si la nueva marca entraría en el top 3.
 *
 * @param entry Entrada del ejercicio con su top de marcas.
 * @param usuarioId ID del usuario actual que está intentando enviar una marca.
 * @param getUsername Función para obtener el nombre de usuario a partir de su ID.
 * @param onClose Callback que se ejecuta al cerrar el diálogo.
 * @param checkWouldEnter Callback que verifica si la marca propuesta entraría en el top 3.
 * @param onRequestSubmit Callback que se ejecuta al enviar la marca con el vídeo opcional.
 * @param onPlayVideo Callback que se ejecuta para reproducir el vídeo de una marca existente.
 */
@Composable
fun DialogTopDetails(
    entry: RecordExerciseEntry,
    usuarioId: Int,
    getUsername: (Int) -> String,
    onClose: () -> Unit,
    checkWouldEnter: (RecordSubmission, (Boolean) -> Unit) -> Unit,
    onRequestSubmit: (RecordSubmission, Uri?) -> Unit,
    onPlayVideo: (String) -> Unit
) {
    var pesoText by remember { mutableStateOf("") }
    var repsText by remember { mutableStateOf("") }
    val selectedVideoUri = remember { mutableStateOf<Uri?>(null) }
    var permitirEnviar by remember { mutableStateOf(false) }

    val videoPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedVideoUri.value = uri
        val peso = pesoText.toDoubleOrNull()
        val reps = repsText.toIntOrNull()
        if (peso != null && reps != null) {
            val candidate = RecordSubmission(usuarioId = usuarioId, peso = peso, repeticiones = reps, fecha = LocalDate.now().toString())
            checkWouldEnter(candidate) { canEnter ->
                permitirEnviar = canEnter
            }
        } else {
            permitirEnviar = false
        }
    }

    fun evaluar() {
        val peso = pesoText.toDoubleOrNull()
        val reps = repsText.toIntOrNull()
        if (peso != null && reps != null) {
            val candidate = RecordSubmission(usuarioId = usuarioId, peso = peso, repeticiones = reps, fecha = LocalDate.now().toString())
            checkWouldEnter(candidate) { canEnter ->
                permitirEnviar = canEnter
            }
        } else {
            permitirEnviar = false
        }
    }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = {
                val peso = pesoText.toDoubleOrNull() ?: 0.0
                val reps = repsText.toIntOrNull() ?: 0
                val candidate = RecordSubmission(usuarioId = usuarioId, peso = peso, repeticiones = reps, fecha = LocalDate.now().toString())
                onRequestSubmit(candidate, selectedVideoUri.value)
            }, enabled = (permitirEnviar && selectedVideoUri.value != null)) {
                Text("Enviar marca")
            }
        },
        dismissButton = {
            Button(onClick = onClose) { Text("Cerrar") }
        },
        title = { Text(entry.nombre) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Top 3:")
                val top = entry.tops.take(3)
                for ((index, t) in top.withIndex()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    val username = getUsername(t.usuarioId)
                    Text("${index + 1}. ${t.peso} · ${t.repeticiones} rep - Usuario nº ${t.usuarioId} (${username})")
                }

                Spacer(modifier = Modifier.height(12.dp))

                val top1 = entry.tops.firstOrNull()
                if (top1?.videoPath != null) {
                    Button(onClick = { onPlayVideo(top1.videoPath) }) { Text("Reproducir vídeo Top 1") }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = pesoText,
                    onValueChange = {
                        pesoText = it
                        evaluar()
                    },
                    label = { Text("Peso (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = repsText,
                    onValueChange = {
                        repsText = it
                        evaluar()
                    },
                    label = { Text("Repeticiones") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = { videoPicker.launch("video/*") }, modifier = Modifier.fillMaxWidth()) {
                    Text(if (selectedVideoUri.value == null) "Seleccionar vídeo (obligatorio)" else "Vídeo seleccionado")
                }
            }
        }
    )
}
