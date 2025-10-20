package com.example.level_up.ui
/*Registro Screen*/

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.level_up.navigation.UsuarioUiState
import com.example.level_up.viewmodel.UsuarioViewModel
import androidx.navigation.NavController


@Composable
fun RegistroScreen(
    navController: NavController,
    view: UsuarioViewModel
) {
    val estado by view.estado.collectAsState(initial = UsuarioUiState())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Campo nombre
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = view::onNombreChange,
            label = { Text("Primer Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo correo
        OutlinedTextField(
            value = estado.correo,
            onValueChange = view::onCorreoChange,
            label = { Text("Correo electrónico") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo contraseña
        OutlinedTextField(
            value = estado.clave,
            onValueChange = view::onClaveChange,
            label = { Text("Contraseña") },
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo dirección
        OutlinedTextField(
            value = estado.direccion,
            onValueChange = view::onDireccionChange,
            label = { Text("Dirección") },
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = estado.edad,
            onValueChange = view::onEdadChange,
            label = { Text("Edad") },
            isError = estado.errores.edad != null,
            supportingText = {
                estado.errores.edad?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )


        // Checkbox de aceptación de términos
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = view::onAceptarTerminosChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Acepto los términos y condiciones")
        }
        Button(
            onClick = {
                if (view.validarFormulario()) {
                    navController.navigate("Resumen")
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }


}
