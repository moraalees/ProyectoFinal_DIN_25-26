package com.example.gymtracker.ui.screens.records

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymtracker.model.MarcaPersonal
import java.time.format.DateTimeFormatter

@Composable
fun FilaMarcaEjercicio(
    marca: MarcaPersonal,
    modifier: Modifier = Modifier
) {
    val fecha = try {
        marca.fecha.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    } catch (_: Exception) {
        "Desconocida"
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable {  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = marca.nombreEjercicio,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${marca.pesoMaximo ?: "—"} kg · ${marca.repeticionesMaximas ?: "—"} rep",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = fecha,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
