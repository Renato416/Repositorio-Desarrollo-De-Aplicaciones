package com.example.level_up.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level_up.navigation.UsuarioUiState
import com.example.level_up.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState(initial = UsuarioUiState())
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Nombre
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Primer Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Correo
        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Correo electrónico") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        OutlinedTextField(
            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Contraseña") },
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Dirección
        OutlinedTextField(
            value = estado.direccion,
            onValueChange = viewModel::onDireccionChange,
            label = { Text("Dirección") },
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Edad
        OutlinedTextField(
            value = estado.edad,
            onValueChange = viewModel::onEdadChange,
            label = { Text("Edad") },
            isError = estado.errores.edad != null,
            supportingText = {
                estado.errores.edad?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Checkbox de aceptación de términos
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = viewModel::onAceptarTerminosChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Acepto los términos y condiciones")
        }

        // Botón de registro
        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    viewModel.registrarUsuario(context)  // Guardar en la BD o SharedPreferences
                    navController.navigate("home") {
                        popUpTo("registro") { inclusive = true } // evita volver al registro
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}
