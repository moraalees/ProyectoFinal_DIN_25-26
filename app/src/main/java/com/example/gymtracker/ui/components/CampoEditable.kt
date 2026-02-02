package com.example.gymtracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtracker.ui.theme.RojoError

/**
 * Composable que muestra un campo de texto editable con validación de errores.
 *
 * @param label Texto que se muestra como etiqueta del campo.
 * @param valorActual Valor inicial o actual del campo.
 * @param validadorErrores Función opcional que recibe el texto actual y devuelve un mensaje de error o null si es válido.
 * @param onGuardar Callback que se ejecuta al guardar el nuevo valor.
 */
@Composable
fun CampoEditable(
    label: String,
    valorActual: String,
    validadorErrores: ((String) -> String?)? = null,
    onGuardar: (String) -> Unit
) {
    var editando by rememberSaveable { mutableStateOf(false) }
    var texto by rememberSaveable(valorActual) { mutableStateOf(valorActual) }
    var mensajeError by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, color = Color.White)

        OutlinedTextField(
            value = texto,
            onValueChange = {
                texto = it
                mensajeError = validadorErrores?.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth(0.6f),
            shape = RoundedCornerShape(12.dp),
            readOnly = !editando,
            isError = mensajeError != null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,

                cursorColor = Color.Black,

                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,

                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )

        if (mensajeError != null) {
            Text(
                text = mensajeError!!,
                color = RojoError,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Row {
            if (editando) {
                TextButton(onClick = {
                    texto = valorActual
                    mensajeError = null
                    editando = false
                }) {
                    Text("Cancelar")
                }

                TextButton(onClick = {
                    val error = validadorErrores?.invoke(texto)
                    if (error == null) {
                        onGuardar(texto)
                        editando = false
                    } else {
                        mensajeError = error
                    }
                }) {
                    Text(
                        text = "Guardar"
                    )
                }
            } else {
                TextButton(onClick = { editando = true }) {
                    Text(
                        text = "Editar"
                    )
                }
            }
        }
    }
}
