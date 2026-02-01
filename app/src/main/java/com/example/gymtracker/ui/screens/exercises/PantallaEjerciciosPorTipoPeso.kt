package com.example.gymtracker.ui.screens.exercises

import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.gymtracker.R
import com.example.gymtracker.data.repository.EjerciciosRepository
import com.example.gymtracker.model.EjercicioBase
import com.example.gymtracker.model.enum_classes.Musculo
import com.example.gymtracker.model.enum_classes.TipoPeso
import com.example.gymtracker.ui.components.EjercicioComponent
import com.example.gymtracker.ui.components.EnumDropdown
import com.example.gymtracker.ui.controllers.ControladorSesion
import com.example.gymtracker.ui.theme.AzulOscuroFondo
import kotlin.collections.chunked
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach

@Composable
fun PantallaEjerciciosPorTipoPeso(context: Context){

    val usuarioActual = ControladorSesion.usuarioLogueado()
    if (usuarioActual == null) {
        Text("No hay usuario logueado")
        return
    }
    val usuarioId = usuarioActual.id

    EjerciciosRepository.inicializar(context, usuarioId)

    var listaEjercicios = EjerciciosRepository.obtenerTodosLosEjercicios()

    val ejerciciosPorTipoPeso = remember {
        agruparTipoPesoCompleto(listaEjercicios)
    }
    var mostrarDialog by rememberSaveable { mutableStateOf(false) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialog = true },
                containerColor = Color(0xFF32437E),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir ejercicio")
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDesvanecido)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "Ejercicios Disponibles",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            ejerciciosPorTipoPeso.forEach { (tipoPeso, listaEjercicios) ->
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tipoPeso.toString()
                                .lowercase()
                                .replace("_", " ")
                                .replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                if (listaEjercicios.isEmpty()){
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay ejercicios registrados con este tipo de peso.",
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(listaEjercicios.chunked(2)) { fila ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            fila.forEach { ejercicio ->
                                Box(modifier = Modifier.weight(1f)) {
                                    EjercicioComponent(ejercicio)
                                }
                            }

                            if (fila.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialog) {

        var nombre by rememberSaveable { mutableStateOf("") }
        var descripcion by rememberSaveable { mutableStateOf("") }
        var tipoPesoSeleccionado by rememberSaveable { mutableStateOf<TipoPeso?>(null) }
        var musculoSeleccionado by rememberSaveable { mutableStateOf<Musculo?>(null) }

        AlertDialog(
            onDismissRequest = { mostrarDialog = false },
            title = { Text("Nuevo ejercicio") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") }
                    )

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") }
                    )

                    EnumDropdown(
                        etiqueta = "Tipo de peso",
                        seleccionado = tipoPesoSeleccionado,
                        valoresEnum = TipoPeso.entries,
                        seleccion = { tipoPesoSeleccionado = it }
                    )

                    EnumDropdown(
                        etiqueta = "Músculos principales",
                        seleccionado = musculoSeleccionado,
                        valoresEnum = Musculo.entries,
                        seleccion = { musculoSeleccionado = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            confirmButton = {
                TextButton(
                    enabled = nombre.isNotBlank()
                            && descripcion.isNotBlank()
                            && tipoPesoSeleccionado != null
                            && musculoSeleccionado != null,
                    onClick = {

                        val listaActual = EjerciciosRepository.obtenerTodosLosEjercicios()

                        val nuevoId = if (listaActual.isEmpty()) {
                            1
                        } else {
                            listaActual.maxOf { it.id } + 1
                        }

                        val nuevoEjercicio = EjercicioBase(
                            id = nuevoId,
                            nombre = nombre,
                            descripcion = descripcion,
                            tipoPeso = tipoPesoSeleccionado!!,
                            musculos = listOf(musculoSeleccionado!!),
                            imagen = R.drawable.logo
                        )

                        EjerciciosRepository.anadirEjercicio(context, usuarioId, nuevoEjercicio)
                        listaEjercicios = EjerciciosRepository.obtenerTodosLosEjercicios()

                        mostrarDialog = false
                    }
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

private fun agruparTipoPesoCompleto(
    listaEjercicios: List<EjercicioBase>
): Map<TipoPeso, List<EjercicioBase>> {

    val mapa = TipoPeso.entries.associateWith { mutableListOf<EjercicioBase>() }

    listaEjercicios.forEach { ejercicio ->
        mapa[ejercicio.tipoPeso]?.add(ejercicio)
    }

    return mapa
}