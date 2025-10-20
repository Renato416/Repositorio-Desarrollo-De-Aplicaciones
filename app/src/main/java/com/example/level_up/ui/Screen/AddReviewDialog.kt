package com.example.level_up.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddReviewDialog(
    onSubmit: (Float, String) -> Unit,
    onDismiss: () -> Unit
) {
    var rating by remember { mutableStateOf(0f) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar reseña") },
        text = {
            Column {
                Text("Calificación (0-5)")
                Slider(value = rating, onValueChange = { rating = it }, valueRange = 0f..5f, steps = 4)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Comentario") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (comment.isNotBlank()) {
                    onSubmit(rating, comment)
                    onDismiss()
                }
            }) {
                Text("Enviar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

