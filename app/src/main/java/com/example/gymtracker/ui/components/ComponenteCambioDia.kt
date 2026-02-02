package com.example.gymtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.DiaSeleccion

/**
 * Composable que representa un día seleccionable con un indicador de activo/inactivo.
 *
 * @param dia Objeto que contiene la etiqueta y el estado activo del día.
 * @param onClick Callback que se ejecuta al pulsar el componente.
 */
@Composable
fun ComponenteCambioDia(
    dia: DiaSeleccion,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (dia.activo) Color(0xFF4CAF50) else Color(0xFFE57373))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dia.label,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
