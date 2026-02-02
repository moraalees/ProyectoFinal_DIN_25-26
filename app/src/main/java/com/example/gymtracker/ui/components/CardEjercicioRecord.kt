package com.example.gymtracker.ui.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymtracker.data.repository.EjerciciosRepository
import com.example.gymtracker.model.RecordExerciseEntry

/**
 * Composable que muestra una tarjeta de un ejercicio con su récord.
 *
 * @param entry Entrada con los datos del ejercicio y sus tops.
 * @param obtenerUsuario Función que recibe un ID de usuario y devuelve su nombre.
 * @param onClick Callback que se ejecuta al hacer click en la tarjeta.
 */
@Composable
fun CardEjercicioRecord(
    entry: RecordExerciseEntry,
    obtenerUsuario: (Int) -> String,
    onClick: () -> Unit) {
    val drawableId = EjerciciosRepository.obtenerEjerciciosPrincipales()
        .firstOrNull { it.id == entry.ejercicioId }?.imagen

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (drawableId != null) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = entry.nombre,
                    alignment = Alignment.Center
                )
            }

            Text(
                text = entry.nombre,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            val top1 = entry.tops.firstOrNull()
            if (top1 != null) {
                val username = obtenerUsuario(top1.usuarioId)
                Text(
                    text = "${top1.peso} · ${top1.repeticiones} rep - Usuario nº ${top1.usuarioId} (${username})",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "Sin marcas",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
