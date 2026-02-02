package com.example.gymtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.gymtracker.model.DiaCalendario

/**
 * Composable que representa un día en el calendario con su número y color según su estado.
 *
 * @param dia Objeto que contiene la fecha, si es del mes actual, si es hoy y los entrenamientos del día.
 * @param mostrarEntreno Callback que se ejecuta al pulsar el día para mostrar los entrenamientos.
 */
@Composable
fun DiaCalendarioItem(
    dia: DiaCalendario,
    mostrarEntreno: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .clickable { mostrarEntreno() }
            .background(
                when {
                    dia.entrenos.isNotEmpty() -> Color(0xFFF17C57)
                    !dia.esDelMesActual -> Color.Transparent
                    else -> Color(0xFFE0E0E0)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dia.fecha.dayOfMonth.toString(),
            color = when {
                dia.esHoy -> Color.White
                !dia.esDelMesActual -> Color.Gray
                else -> Color.Black
            }
        )
    }
}