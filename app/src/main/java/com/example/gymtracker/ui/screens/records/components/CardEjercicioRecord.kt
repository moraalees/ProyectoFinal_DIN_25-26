package com.example.gymtracker.ui.screens.records.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gymtracker.data.records.RecordExerciseEntry

@Composable
fun CardEjercicioRecord(entry: RecordExerciseEntry, getUsername: (Int) -> String, onClick: () -> Unit) {
    val drawableId = com.example.gymtracker.data.repository.EjerciciosRepository.obtenerEjerciciosPrincipales()
        .firstOrNull { it.id == entry.ejercicioId }?.imagen

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .height(160.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (drawableId != null) {
                Image(painter = painterResource(id = drawableId), contentDescription = entry.nombre)
            }

            Text(text = entry.nombre, style = MaterialTheme.typography.titleMedium)

            val top1 = entry.tops.firstOrNull()
            if (top1 != null) {
                val username = getUsername(top1.usuarioId)
                Text(text = "${top1.peso} · ${top1.repeticiones} rep - Usuario nº ${top1.usuarioId} (${username})", style = MaterialTheme.typography.bodySmall)
            } else {
                Text(text = "Sin marcas", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
