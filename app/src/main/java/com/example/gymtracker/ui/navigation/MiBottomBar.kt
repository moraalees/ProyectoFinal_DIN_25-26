package com.example.gymtracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MiBottomBar(navController: NavController){
    val rutaActual = navController
        .currentBackStackEntryAsState().value
        ?.destination?.route

    val indice = when (rutaActual) {
        Rutas.EJERCICIOS.ruta -> 0
        Rutas.EJERCICIOS_MUSCULO.ruta -> 1
        Rutas.EJERCICIOS_TIPO_PESO.ruta -> 2
        else -> 0
    }

    NavigationBar {
        NavigationBarItem(
            selected = indice==0,
            onClick = {
                navController.navigate(Rutas.EJERCICIOS.ruta) {
                    popUpTo(Rutas.EJERCICIOS.ruta) { inclusive = true }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = "Icono de mancuerna"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Black,
                selectedIconColor = Color(0xFF32437E),
                selectedTextColor = Color(0xFF32437E),
                unselectedTextColor = Color.Black,
            ),
            label = { Text("Ejercicios") }
        )
        NavigationBarItem(
            selected = indice==1,
            onClick = {
                navController.navigate(Rutas.EJERCICIOS_MUSCULO.ruta) {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Icono de lista"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Black,
                selectedIconColor = Color(0xFF32437E),
                selectedTextColor = Color(0xFF32437E),
                unselectedTextColor = Color.Black,
            ),
            label = { Text("Ejs Por Musculo") }
        )
        NavigationBarItem(
            selected = indice==2,
            onClick = {
                navController.navigate(Rutas.EJERCICIOS_TIPO_PESO.ruta) {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Scale,
                    contentDescription = "Icono de Escala"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Black,
                selectedIconColor = Color(0xFF32437E),
                selectedTextColor = Color(0xFF32437E),
                unselectedTextColor = Color.Black,
            ),
            label = { Text("Ejs Por Tipo Peso") }
        )
    }
}