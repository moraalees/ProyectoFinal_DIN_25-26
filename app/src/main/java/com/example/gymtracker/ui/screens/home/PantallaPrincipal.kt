package com.example.gymtracker.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.ui.components.EntrenoDelDiaRotativo
import java.util.Calendar

@Composable
fun PantallaPrincipal(
    usuario: Usuario,
    viewModel: HomeViewModel,
    iniciarEntreno: (PlanSemanal) -> Unit
) {
    LaunchedEffect(usuario.id) {
        viewModel.cargarPerfil(usuario)
    }
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )
    val rutina = viewModel.rutinaActiva

    val hoy = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val indiceDia = (hoy + 5) % 7
    val entrenoHoy = rutina?.dias?.getOrNull(indiceDia)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rutina Actual",
                fontSize = 30.sp,
                color = Color.White
            )
            rutina?.let {
                EntrenoDelDiaRotativo(it)
            } ?: Text("No tienes ninguna rutina activa", color = Color.White)
        }

        // Mostrar el botón solo si hay rutina activa y hoy es día de entrenamiento con ejercicios
        if (rutina != null && entrenoHoy != null && entrenoHoy.esEntreno && entrenoHoy.ejercicios.isNotEmpty()){
            IconButton(
                onClick = { iniciarEntreno(rutina) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = "Iniciar entrenamiento",
                    tint = Color.Red,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

    }
}
