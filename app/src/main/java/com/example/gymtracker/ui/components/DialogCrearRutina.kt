package com.example.gymtracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gymtracker.model.DiaSeleccion

@Composable
fun DialogCrearRutina(
    onDismiss: () -> Unit,
    onCrear: (String, List<Boolean>) -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }

    var dias by remember {
        mutableStateOf(
            listOf(
                DiaSeleccion("L", false),
                DiaSeleccion("M", false),
                DiaSeleccion("X", false),
                DiaSeleccion("J", false),
                DiaSeleccion("V", false),
                DiaSeleccion("S", false),
                DiaSeleccion("D", false)
            )
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nueva rutina", fontSize = 22.sp)

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la rutina") }
                )

                Spacer(Modifier.height(16.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    for (fila in 0 until 3) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            for (col in 0 until 2) {
                                val index = fila * 2 + col
                                ComponenteCambioDia(
                                    dia = dias[index],
                                    onClick = {
                                        dias = dias.toMutableList().also {
                                            it[index] = it[index].copy(
                                                activo = !it[index].activo
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }

                    ComponenteCambioDia(
                        dia = dias[6],
                        onClick = {
                            dias = dias.toMutableList().also {
                                it[6] = it[6].copy(activo = !it[6].activo)
                            }
                        }
                    )
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        onCrear(nombre, dias.map { it.activo })
                    },
                    enabled = nombre.isNotBlank()
                ) {
                    Text("Añadir rutina")
                }
            }
        }
    }
}