package com.example.gymtracker.ui.screens.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.gymtracker.ui.screens.records.components.CardEjercicioRecord
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtracker.data.records.LocalRecordsDataSource
import com.example.gymtracker.data.records.RecordsRepository
import com.example.gymtracker.ui.screens.records.components.DialogTopDetails
import com.google.gson.Gson
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import com.example.gymtracker.data.local.json.GuardadoJson
import com.example.gymtracker.data.local.json.UsuarioJsonDataSource
import com.example.gymtracker.data.repository.UsuarioRepository
import com.example.gymtracker.ui.controllers.ControladorSesion

@Composable
fun PantallaMarcasRecords() {
    val contexto = LocalContext.current
    val dataSource = LocalRecordsDataSource(contexto, Gson())
    val repo = RecordsRepository(dataSource)

    val guardado = GuardadoJson(contexto)
    val usuarioDataSource = UsuarioJsonDataSource(guardado, Gson())
    val usuarioRepo = UsuarioRepository(usuarioDataSource)

    LaunchedEffect(Unit) {
        ControladorSesion.cargarSesion(contexto, usuarioDataSource)
    }
    val usuarioActual = ControladorSesion.usuarioLogueado()
    val currentUserId = usuarioActual?.id ?: -1

    val vm: RecordsViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RecordsViewModel(repo) as T
        }
    })

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )

    var selectedEntry by remember { mutableStateOf<com.example.gymtracker.data.records.RecordExerciseEntry?>(null) }
    var mostrarDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.loadTopsForAll()
    }

    val uiState = vm.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ){
        Text(text = "Records de ejercicios", color = Color.White)

        val ejercicios = when (val state = uiState.value) {
            is RecordsUiState.Loading -> emptyList()
            is RecordsUiState.Error -> emptyList()
            is RecordsUiState.Success -> state.ejercicios
            else -> emptyList()
        }

        val lazyGridState = rememberLazyGridState()

        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(2),
        ){
            items(ejercicios) { ejercicio ->
                CardEjercicioRecord(entry = ejercicio, getUsername = { id -> usuarioRepo.obtenerUsuarioPorId(id)?.nombreUsuario ?: "Usuario$id" }) {
                    selectedEntry = ejercicio
                    mostrarDialog = true
                }
            }
        }

        if (mostrarDialog && selectedEntry != null) {
            DialogTopDetails(
                entry = selectedEntry!!,
                usuarioId = currentUserId,
                getUsername = { id -> usuarioRepo.obtenerUsuarioPorId(id)?.nombreUsuario ?: "Usuario$id" },
                onClose = { mostrarDialog = false },
                checkWouldEnter = { candidate, callback -> vm.wouldEnterTop3(selectedEntry!!.ejercicioId, candidate, callback) },
                onRequestSubmit = { candidate, videoUri ->
                    if (currentUserId <= 0) {
                        return@DialogTopDetails
                    }
                    val fixedCandidate = candidate.copy(usuarioId = currentUserId)
                    vm.attemptSubmit(selectedEntry!!.ejercicioId, fixedCandidate.usuarioId, fixedCandidate, videoUri)
                },
                onPlayVideo = { relativePath ->
                    val file = java.io.File(contexto.filesDir, "records/$relativePath")
                    val uri: Uri = androidx.core.content.FileProvider.getUriForFile(contexto, contexto.packageName + ".fileprovider", file)
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                        setDataAndType(uri, "video/*")
                        addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    contexto.startActivity(intent)
                }
            )
        }
    }
}