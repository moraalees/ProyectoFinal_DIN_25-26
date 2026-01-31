package com.example.gymtracker.ui.screens.actual_training

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.model.PlanSemanal
import com.example.gymtracker.model.SerieResultado
import com.example.gymtracker.ui.utils.cancelarNotificacionDescanso
import com.example.gymtracker.ui.utils.mostrarNotificacionDescanso
import com.example.gymtracker.ui.utils.mostrarNotificacionFinDescanso
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@Composable
fun PantallaInicioEntreno(
    plan: PlanSemanal,
    viewModel: EntrenamientoViewModel,
    context: Context,
    navegarHome: () -> Unit
) {
    val hoy = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val indiceDia = (hoy + 5) % 7
    val entrenoHoy = plan.dias[indiceDia]

    if (!entrenoHoy.esEntreno || entrenoHoy.ejercicios.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (!entrenoHoy.esEntreno) "Descanso" else "No hay ejercicios para hoy.",
                color = Color.White,
                fontSize = 24.sp
            )
        }
        return
    }

    var indiceEjercicio by rememberSaveable { mutableIntStateOf(0) }
    val ejercicioActual = entrenoHoy.ejercicios[indiceEjercicio]

    var serieActual by rememberSaveable { mutableIntStateOf(1) }
    var repsHechas by rememberSaveable { mutableStateOf("") }
    var pesoUsado by rememberSaveable { mutableStateOf("") }

    var enDescanso by remember { mutableStateOf(false) }
    var tiempoRestante by remember { mutableIntStateOf(120) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )

    fun avanzarSerieOEjercicio() {
        val ejercicio = entrenoHoy.ejercicios[indiceEjercicio]
        if (serieActual < ejercicio.series) {
            serieActual++
        } else if (indiceEjercicio < entrenoHoy.ejercicios.size - 1) {
            indiceEjercicio++
            serieActual = 1
        } else {
            val ejerciciosGuardados = entrenoHoy.ejercicios.map { it.copiaGuardada() }.toMutableList()
            val entrenoRealizado = Entreno(
                nombre = entrenoHoy.nombre,
                ejercicios = ejerciciosGuardados,
                enfoque = entrenoHoy.enfoque,
                esEntreno = true,
                tiempoDescanso = entrenoHoy.tiempoDescanso,
                fecha = LocalDate.now().toString()
            )
            viewModel.guardarEntrenamiento(entrenoRealizado)
            navegarHome()
        }
        repsHechas = ""
        pesoUsado = ""
    }

    LaunchedEffect(enDescanso) {
        if (enDescanso) {
            tiempoRestante = 120
            mostrarNotificacionDescanso(context, tiempoRestante)

            while (tiempoRestante > 0 && enDescanso) {
                delay(1000)
                tiempoRestante--
                mostrarNotificacionDescanso(context, tiempoRestante)
            }

            cancelarNotificacionDescanso(context)
            mostrarNotificacionFinDescanso(context)
            enDescanso = false
            avanzarSerieOEjercicio()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = plan.nombre.replaceFirstChar { it.uppercase() },
                fontSize = 28.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${ejercicioActual.ejercicio.nombre} - Serie $serieActual / ${ejercicioActual.series}",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painter = painterResource(ejercicioActual.ejercicio.imagen),
                        contentDescription = ejercicioActual.ejercicio.nombre,
                        modifier = Modifier.size(110.dp)
                    )
                    Text(
                        text = ejercicioActual.ejercicio.descripcion,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (!enDescanso) {
                        OutlinedTextField(
                            value = repsHechas,
                            onValueChange = { repsHechas = it.filter { c -> c.isDigit() } },
                            label = { Text("Repeticiones hechas") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = pesoUsado,
                            onValueChange = { pesoUsado = it.filter { c -> c.isDigit() || c == '.' } },
                            label = { Text("Peso usado (kg)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (repsHechas.isNotBlank()) {
                                    // Añadir la serie realizada a la lista del ejercicio en lugar de sobreescribir
                                    val reps = repsHechas.toInt()
                                    val peso = pesoUsado.toDoubleOrNull() ?: 0.0
                                    ejercicioActual.seriesRealizadas.add(SerieResultado(reps, peso))
                                    ejercicioActual.pesoEstimado = peso
                                    if (pesoUsado.isBlank() || peso == 0.0) {
                                        Toast.makeText(context, "Peso no válido, se usará 0 kg", Toast.LENGTH_SHORT).show()
                                    }
                                    enDescanso = true
                                    repsHechas = ""
                                    pesoUsado = ""
                                }
                            },
                            enabled = repsHechas.isNotBlank()
                        ) {
                            Text("Completar serie")
                        }
                    } else {
                        val minutos = tiempoRestante / 60
                        val segundos = tiempoRestante % 60
                        Text(
                            text = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos),
                            fontSize = 32.sp,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Button(onClick = { tiempoRestante += 10 }) {
                                Text("+10s")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(onClick = {
                                enDescanso = false
                                avanzarSerieOEjercicio()
                            }) {
                                Text("Terminar descanso")
                            }
                        }
                    }
                }
            }
        }
    }
}