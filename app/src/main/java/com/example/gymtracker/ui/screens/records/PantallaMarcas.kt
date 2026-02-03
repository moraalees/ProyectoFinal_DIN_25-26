package com.example.gymtracker.ui.screens.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymtracker.data.repository.EntrenamientosRepository
import com.example.gymtracker.ui.components.FilaMarcaEjercicio
import com.example.gymtracker.ui.theme.AzulOscuroFondo

/**
 * PantallaMarcas
 *
 * Composable que muestra las marcas personales de un usuario en la aplicación.
 * Permite buscar ejercicios por nombre y visualizar los registros de peso máximo y repeticiones.
 *
 * Parámetros:
 * @param entrenamientosRepository Repositorio para obtener los entrenamientos del usuario.
 * @param usuarioId ID del usuario cuyos datos se mostrarán; si es null, se muestra un mensaje de error.
 *
 * Funcionalidades:
 * - Muestra un TextField para filtrar ejercicios por nombre.
 * - Observa el estado de MarcasViewModel (Loading, Success, Error).
 * - Muestra un indicador de carga mientras se obtienen las marcas.
 * - Muestra un mensaje de error si falla la carga.
 * - Lista las marcas personales con LazyColumn si la carga es exitosa.
 */
@Composable
fun PantallaMarcas(
    entrenamientosRepository: EntrenamientosRepository,
    usuarioId: Int?
) {
    var textoBusqueda by rememberSaveable { mutableStateOf("") }
    val viewModel = remember { MarcasViewModel(entrenamientosRepository) }

    LaunchedEffect(usuarioId) {
        if (usuarioId != null) viewModel.cargarMarcas(usuarioId)
    }

    val uiState by viewModel.uiState.collectAsState()

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
            .padding(16.dp)
    ) {
        if (usuarioId == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Usuario no identificado")
            }
            return
        }

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = { Text("Buscar ejercicio...") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

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

                val marcasFiltradas = marcas.filter {
                    it.nombreEjercicio.contains(textoBusqueda, ignoreCase = true)
                }

                if (marcasFiltradas.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay resultados",
                            color = Color.White
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(marcasFiltradas) { marca ->
                            FilaMarcaEjercicio(marca = marca)
                        }
                    }
                }
            }
        }
    }
}