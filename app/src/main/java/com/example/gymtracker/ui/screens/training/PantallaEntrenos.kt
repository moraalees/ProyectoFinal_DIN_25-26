package com.example.gymtracker.ui.screens.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.Usuario
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.ui.screens.routines.RutinasViewModel

@Composable
fun PantallaEntrenos(
    rutina: PlanSemanal,
    onEntrenoClick: (Entreno, PlanSemanal) -> Unit
) {
    val diasAbreviados = listOf("L", "M", "X", "J", "V", "S", "D")

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = rutina.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(60.dp))

        rutina.dias.forEachIndexed { index, entreno ->
            val entrenoTexto = if (entreno.esEntreno) "Entreno" else "Descanso"

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = diasAbreviados.getOrElse(index) { "" },
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = entrenoTexto,
                    color = if (entreno.esEntreno) Color.Green else Color.Red
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    onClick = { onEntrenoClick(entreno, rutina) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "Icono de opciones",
                        tint = Color.White
                    )
                }
            }
        }
    }
}