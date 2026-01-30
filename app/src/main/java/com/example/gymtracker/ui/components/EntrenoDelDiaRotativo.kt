package com.example.gymtracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.PlanSemanal
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun EntrenoDelDiaRotativo(
    plan: PlanSemanal
) {
    val hoy = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val indiceDia = (hoy + 5) % 7

    val entrenoHoy = plan.dias[indiceDia]

    var ejercicioIndex by remember { mutableIntStateOf(0) }

    if (entrenoHoy.ejercicios.isNotEmpty()) {
        LaunchedEffect(key1 = entrenoHoy) {
            while (true) {
                delay(3000)
                ejercicioIndex = (ejercicioIndex + 1) % entrenoHoy.ejercicios.size
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!entrenoHoy.esEntreno) {
            Text(
                text = "Descanso",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        } else if (entrenoHoy.ejercicios.isEmpty()){
            Text(
                text = "No hay ejercicios para hoy.",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        } else {
            val ejercicioActual = entrenoHoy.ejercicios[ejercicioIndex]

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = plan.nombre.lowercase()
                            .replace("_", " ")
                            .split(" ")
                            .joinToString(" ") {
                                it.replaceFirstChar { c -> c.uppercase() }
                            },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Image(
                        painter = painterResource(id = ejercicioActual.ejercicio.imagen),
                        contentDescription = ejercicioActual.ejercicio.nombre,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 8.dp),
                        alignment = Alignment.Center
                    )

                    Text(
                        text = ejercicioActual.ejercicio.nombre,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Series: ${ejercicioActual.series} x Reps: ${ejercicioActual.repeticiones.first}-${ejercicioActual.repeticiones.last} " +
                                (ejercicioActual.pesoEstimado?.let { "- Peso: ${it} kg" } ?: "0"),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
