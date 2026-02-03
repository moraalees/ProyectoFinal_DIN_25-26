package com.example.gymtracker.ui.screens.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.model.DiaCalendario
import com.example.gymtracker.model.Entreno
import com.example.gymtracker.ui.components.DiaCalendarioItem
import com.example.gymtracker.ui.screens.actual_training.EntrenamientoViewModel
import com.example.gymtracker.ui.theme.AzulOscuroFondo
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Pantalla de calendario de entrenamientos.
 *
 * Muestra un calendario mensual donde se visualizan los días con entrenamientos
 * realizados por el usuario. Permite navegar entre meses y seleccionar un día
 * concreto para ver el detalle del entreno efectuado en esa fecha.
 *
 * Funcionalidades principales:
 * - Navegación entre meses (anterior / siguiente).
 * - Resaltado de días con entrenamientos.
 * - Selección de un día para ver el detalle del entreno.
 * - Carga automática de entrenamientos desde el ViewModel.
 *
 * @param entrenamientoViewModel ViewModel que gestiona y proporciona los entrenamientos realizados.
 */
@Composable
fun PantallaCalendario(
    entrenamientoViewModel: EntrenamientoViewModel
) {
    var mesActual by rememberSaveable { mutableStateOf(YearMonth.now()) }
    val hoy = LocalDate.now()

    val entrenamientosRealizados = entrenamientoViewModel.entrenamientos

    val dias by remember(entrenamientosRealizados, mesActual) {
        derivedStateOf {
            generarDiasCalendario(mesActual, hoy, entrenamientosRealizados)
        }
    }
    var diaSeleccionado by remember { mutableStateOf<DiaCalendario?>(null) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    LaunchedEffect(Unit) {
        entrenamientoViewModel.cargarDatos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { mesActual = mesActual.minusMonths(1) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Mes anterior",
                    tint = Color.White
                )
            }

            Text(
                text = mesActual.month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es"))
                    .replaceFirstChar { it.uppercase() } +
                        " ${mesActual.year}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            IconButton(
                onClick = { mesActual = mesActual.plusMonths(1) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Mes siguiente",
                    tint = Color.White
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("L", "M", "X", "J", "V", "S", "D").forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dias) { dia ->
                DiaCalendarioItem(dia = dia) {
                    diaSeleccionado = dia
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.White)
                .padding(16.dp)
        ) {
            if (diaSeleccionado == null) {
                Text("Selecciona un día para ver su entreno", fontSize = 16.sp)
            } else {
                val entrenos = diaSeleccionado!!.entrenos
                if (entrenos.isEmpty()) {
                    Text("Este día no hubo entreno", fontSize = 16.sp)
                } else {
                    val entreno = entrenos.first()
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
                    ) {
                        item {
                            Text(
                                text = "Día ${diaSeleccionado!!.fecha.dayOfMonth} de " +
                                        diaSeleccionado!!.fecha.month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es"))
                                            .replaceFirstChar { it.uppercase() },
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(entreno.ejercicios) { ejercicio ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color.Gray)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = ejercicio.ejercicio.nombre,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    if (ejercicio.seriesRealizadas.isEmpty()) {
                                        Text(
                                            text = "Series: ${ejercicio.series}",
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Repeticiones: ${ejercicio.repeticiones.first} - ${ejercicio.repeticiones.last}",
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Peso: ${ejercicio.pesoEstimado ?: 0.0} kg",
                                            fontSize = 14.sp
                                        )
                                    } else {
                                        ejercicio.seriesRealizadas.forEachIndexed { index, serie ->
                                            Text(
                                                text = "Serie ${index + 1}: ${serie.repeticiones} rep - ${serie.peso} kg",
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Genera la lista de días que se muestran en el calendario mensual.
 *
 * La función construye una cuadrícula completa de semanas (lunes a domingo),
 * incluyendo días del mes anterior y siguiente si es necesario, para mantener
 * la estructura del calendario.
 *
 * Cada día contiene información sobre:
 * - Si pertenece al mes actual.
 * - Si corresponde al día de hoy.
 * - Los entrenamientos realizados en esa fecha.
 *
 * @param mes Mes y año a mostrar en el calendario.
 * @param hoy Fecha actual del sistema.
 * @param entrenosRealizados Lista de entrenamientos guardados.
 *
 * @return Lista de objetos [DiaCalendario] que representan el calendario completo.
 */
private fun generarDiasCalendario(
    mes: YearMonth,
    hoy: LocalDate,
    entrenosRealizados: List<Entreno>
): List<DiaCalendario> {
    val entrenosPorFecha = entrenosRealizados.groupBy { LocalDate.parse(it.fecha) }

    val primerDiaMes = mes.atDay(1)
    val ultimoDiaMes = mes.atEndOfMonth()

    val inicioCalendario = primerDiaMes.minusDays(
        ((primerDiaMes.dayOfWeek.value + 6) % 7).toLong()
    )
    val finCalendario = ultimoDiaMes.plusDays(
        (7 - ultimoDiaMes.dayOfWeek.value).toLong()
    )
    val dias = mutableListOf<DiaCalendario>()
    var fecha = inicioCalendario
    while (!fecha.isAfter(finCalendario)) {
        dias.add(
            DiaCalendario(
                fecha = fecha,
                esDelMesActual = fecha.month == mes.month,
                esHoy = fecha == hoy,
                entrenos = entrenosPorFecha[fecha] ?: emptyList()
            )
        )
        fecha = fecha.plusDays(1)
    }
    return dias
}
