package com.example.gymtracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Composable genérico para mostrar un dropdown de selección basado en un enum.
 *
 * Muestra un botón con la opción actualmente seleccionada o una etiqueta por defecto.
 * Al hacer clic, se despliega un menú con todas las opciones del enum `valoresEnum`.
 * La opción seleccionada se notifica mediante la lambda `seleccion`.
 *
 * El texto de cada opción se transforma para reemplazar guiones bajos por espacios
 * y capitalizar la primera letra de cada palabra.
 *
 * @param T Tipo genérico del enum.
 * @param etiqueta Texto a mostrar cuando no hay ninguna opción seleccionada.
 * @param seleccionado Valor actualmente seleccionado del enum, puede ser null.
 * @param valoresEnum Lista de todos los valores del enum disponibles para seleccionar.
 * @param seleccion Lambda que se llama al seleccionar un valor del enum.
 */
@Composable
fun <T> EnumDropdown(
    etiqueta: String,
    seleccionado: T?,
    valoresEnum: List<T>,
    seleccion: (T) -> Unit
) {
    var enumExpandidos by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = { enumExpandidos = true },
            modifier = Modifier.fillMaxWidth(0.6f),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            val textoAMostrar = seleccionado?.toString()?.lowercase()
                ?.replace("_", " ")
                ?.split(" ")
                ?.joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
                ?: etiqueta

            Text(
                text = textoAMostrar,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        DropdownMenu(
            expanded = enumExpandidos,
            onDismissRequest = { enumExpandidos = false },
            modifier = Modifier.background(Color(0xFF0D1B2A))
        ) {
            valoresEnum.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = value.toString().lowercase()
                                .replace("_", " ")
                                .split(" ")
                                .joinToString(" ") {
                                    it.replaceFirstChar { c -> c.uppercase() }
                                },
                            color = Color.White
                        )
                    },
                    onClick = {
                        seleccion(value)
                        enumExpandidos = false
                    }
                )
            }
        }
    }
}
