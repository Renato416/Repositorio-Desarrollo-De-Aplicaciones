package com.example.level_up.ui.Screen
/*ResumenScreen*/

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.level_up.viewmodel.UsuarioViewModel


@Composable
fun ResumenScreen(viewModel: UsuarioViewModel) {
    val estado by viewModel.estado.collectAsState()
    Column(Modifier.padding(16.dp)) {
        Text("Resumen del Registro", style = MaterialTheme.typography.headlineMedium)
        Text("Nombre:${estado.nombre}")
        Text("Correo: ${estado.correo}")
        Text("Dirección: ${estado.direccion}")
        Text("Edad: ${estado.edad}")
        Text("Contraseña: ${"*".repeat(estado.clave.length)}")
        Text("Terminos: ${if (estado.aceptaTerminos)"Aceptados" else "No aceptado"}")
    }
}