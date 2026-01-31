package com.example.gymtracker.ui.screens.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.gymtracker.data.repository.EjerciciosRepository

@Composable
fun PantallaMarcasRecords() {
    val ejercicios = EjerciciosRepository.obtenerEjerciciosPrincipales()

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(Color(0xFF32437E), Color.Black)
    )

    val lazyGridState = rememberLazyGridState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ){
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(2),
        ){
            {
                items(ejercicios) { ejercicio ->

                }
            }
        }
    }
}