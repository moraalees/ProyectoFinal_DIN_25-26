package com.example.gymtracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.gymtracker.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiTopBar(
    usuario: Usuario,
    navegarPantallaCredenciales: () -> Unit,
    desplegarMenu: () -> Unit,
    mostrarMenu: Boolean = true
) {
    TopAppBar(
        title = {
            Text(
                text = usuario.nombreUsuario,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navegarPantallaCredenciales
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icono de perfil",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            if (mostrarMenu) {
                IconButton(
                    onClick = desplegarMenu
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Icono del menú"
                    )
                }
            }
        }
    )
}
