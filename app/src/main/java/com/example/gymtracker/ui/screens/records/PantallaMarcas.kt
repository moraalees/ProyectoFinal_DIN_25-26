package com.example.gymtracker.ui.screens.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.gymtracker.data.repository.EntrenamientosRepository

@Composable
fun PantallaMarcas(
    entrenamientosRepository: EntrenamientosRepository,
    usuarioId: Int?
) {
    val viewModel = remember { MarcasViewModel(entrenamientosRepository) }

    LaunchedEffect(usuarioId) {
        if (usuarioId != null) viewModel.cargarMarcas(usuarioId)
    }

    val uiState by viewModel.uiState.collectAsState()

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(fondoDesvanecido)
    ) {
        if (usuarioId == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Usuario no identificado")
            }
            return
        }

        when (uiState) {
            is MarcasUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is MarcasUiState.Error -> {
                val mensaje = (uiState as MarcasUiState.Error).mensaje
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = mensaje)
                }
            }
            is MarcasUiState.Success -> {
                val marcas = (uiState as MarcasUiState.Success).marcas
                if (marcas.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No hay marcas registradas todavía")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(marcas) { marca ->
                            FilaMarcaEjercicio(marca = marca)
                        }
                    }
                }
            }
        }
    }

}