package com.example.gymtracker.ui.screens.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.ui.components.RutinaSemanalComponente
import com.example.gymtracker.ui.components.DialogCrearRutina
import com.example.gymtracker.ui.navigation.Rutas
import com.example.gymtracker.ui.theme.AzulOscuroFondo

@Composable
fun PantallaRutinas(
    rutinaViewModel: RutinasViewModel,
    usuario: Usuario,
    onModificarRutina: (PlanSemanal) -> Unit
) {
    val perfil = rutinaViewModel.obtenerPerfil(usuario.id)

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    var mostrarDialog by rememberSaveable { mutableStateOf(false) }

    if (perfil == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDesvanecido),
            contentAlignment = Alignment.Center
        ) {
            Text("Este usuario no tiene perfil de gimnasio", color = Color.White)
        }
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Crear rutina")
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDesvanecido)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(perfil.rutinas) { rutina ->
                RutinaSemanalComponente(
                    plan = rutina,
                    rutinaViewModel = rutinaViewModel,
                    perfil = perfil,
                    onModificar = onModificarRutina
                )
                Spacer(Modifier.height(12.dp))
            }
        }

        if (mostrarDialog) {
            DialogCrearRutina(
                onDismiss = { mostrarDialog = false },
                onCrear = { nombre, diasEntreno ->
                    rutinaViewModel.crearRutinaPersonalizada(
                        perfil = perfil,
                        nombre = nombre,
                        diasEntreno = diasEntreno
                    )
                    mostrarDialog = false
                }
            )
        }
    }
}