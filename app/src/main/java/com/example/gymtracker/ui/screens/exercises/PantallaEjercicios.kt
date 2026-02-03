package com.example.gymtracker.ui.screens.exercises

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.example.gymtracker.data.local.json.EjerciciosJsonDataSource
import com.example.gymtracker.data.repository.EjerciciosRepository
import com.example.gymtracker.model.EjercicioBase
import com.example.gymtracker.model.enum_classes.Musculo
import com.example.gymtracker.model.enum_classes.TipoPeso
import com.example.gymtracker.ui.components.EjercicioComponent
import com.example.gymtracker.ui.components.EnumDropdown
import com.example.gymtracker.ui.controllers.ControladorSesion
import com.example.gymtracker.ui.theme.AzulOscuroFondo
import com.google.gson.Gson

/**
 * Pantalla de gestión y visualización de ejercicios.
 *
 * Muestra un listado en formato de cuadrícula con todos los ejercicios disponibles
 * para el usuario actualmente logueado. Permite añadir nuevos ejercicios mediante
 * un diálogo modal accesible desde un botón flotante.
 *
 * Funcionalidades principales:
 * - Inicialización del repositorio de ejercicios según el usuario activo.
 * - Visualización de ejercicios en una cuadrícula adaptable.
 * - Creación de nuevos ejercicios con nombre, descripción, tipo de peso y músculo principal.
 * - Validación de campos antes de permitir la creación de un ejercicio.
 *
 * Si no hay un usuario logueado, se muestra un mensaje informativo y la pantalla
 * no continúa con la carga de datos.
 *
 * @param context Contexto de la aplicación necesario para inicializar repositorios
 * y realizar operaciones de persistencia.
 */
@Composable
fun PantallaEjercicios(context: Context) {

    val usuarioActual = ControladorSesion.usuarioLogueado()
    if (usuarioActual == null) {
        Text("No hay usuario logueado")
        return
    }
    val usuarioId = usuarioActual.id

    EjerciciosRepository.inicializar(context, usuarioId)

    var listaEjercicios by remember { mutableStateOf(EjerciciosRepository.obtenerTodosLosEjercicios()) }

    val lazyGridState = rememberLazyGridState()
    var mostrarDialog by rememberSaveable { mutableStateOf(false) }

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(AzulOscuroFondo, Color.Black)
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialog = true },
                containerColor = AzulOscuroFondo,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir ejercicio")
            }
        },
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDesvanecido)
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Ejercicios Disponibles",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2)
            ) {
                items(listaEjercicios) { ejercicio ->
                    EjercicioComponent(ejercicio)
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
