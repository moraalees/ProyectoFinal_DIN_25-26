package com.example.gymtracker.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymtracker.ui.theme.AzulOscuroFondo

@Composable
fun PantallaAdmin(
    pantallaRecords: () -> Unit,
    cerrarSesion: () -> Unit
){
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(fondoDesvanecido)
            .fillMaxSize()
    ) {
        Button(
            onClick = { pantallaRecords() }
        ) {
            Text(
                text = "Records de marcas"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { cerrarSesion() }
        ) {
            Text(
                text = "Cerrar sesión"
            )
        }
    }
}