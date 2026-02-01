package com.example.gymtracker.ui.screens.admin

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtracker.data.repository.RecordsRepository
import com.example.gymtracker.model.RecordRequest
import com.example.gymtracker.data.local.json.LocalRecordsDataSource
import com.example.gymtracker.ui.screens.records.SolicitudesRecordViewModel
import com.example.gymtracker.data.local.json.GuardadoJson
import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.ui.theme.AzulOscuroFondo
import com.google.gson.Gson

@Composable
fun PantallaManejoMarcas(
    onBack: () -> Unit
) {
    val contexto = LocalContext.current
    val guardado = GuardadoJson(contexto)
    val usuarioDataSource = UsuarioJsonDataSource(guardado, Gson())
    val usuarioRepo = UsuarioRepository(usuarioDataSource)

    val dataSource = LocalRecordsDataSource(contexto, Gson())
    val repo = RecordsRepository(dataSource)

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    val viewModel: SolicitudesRecordViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SolicitudesRecordViewModel(repo) as T
        }
    })

    LaunchedEffect(Unit) { viewModel.loadRequests() }

    val solicitudes = viewModel.solicitudes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Solicitudes de marcas",
            modifier = Modifier.padding(12.dp),
            color = Color.White
        )

        for (solicitud in solicitudes.value) {
            RequestCard(req = solicitud, getUsername = { id -> usuarioRepo.obtenerUsuarioPorId(id)?.nombreUsuario ?: "Usuario$id" }, onAccept = { viewModel.accept(solicitud.id) }, onReject = { viewModel.reject(solicitud.id) })
        }

        Button(onClick = onBack, modifier = Modifier.padding(12.dp)) { Text("Volver") }
    }
}

@Composable
fun RequestCard(req: RecordRequest, getUsername: (Int) -> String, onAccept: () -> Unit, onReject: () -> Unit) {
    val contexto = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Ejercicio: ${req.ejercicioId}")
                Text("Peso: ${req.submission.peso} · ${req.submission.repeticiones} rep - Usuario nº ${getUsername(req.submission.usuarioId)}")
                Row {
                    Button(onClick = {
                        try {
                            val file = java.io.File(contexto.filesDir, "records/${req.videoPath}")
                            val uri: Uri = androidx.core.content.FileProvider.getUriForFile(contexto, contexto.packageName + ".fileprovider", file)
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "video/*")
                                addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            contexto.startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(contexto, "No se pudo abrir el vídeo (FileProvider no configurado)", Toast.LENGTH_LONG).show()
                        }
                    }) { Text("Ver vídeo") }

                    Button(onClick = onAccept, modifier = Modifier.padding(start = 8.dp)) { Text("Aceptar") }
                    Button(onClick = onReject, modifier = Modifier.padding(start = 8.dp)) { Text("Rechazar") }
                }
            }
        }
    }
}