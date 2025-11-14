package com.example.level_up.ui.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

    // Estado para controlar la visibilidad del diálogo de términos
    var showTermsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Para permitir scroll si el contenido es mucho
        horizontalAlignment = Alignment.CenterHorizontally // Centrar el título
    ) {

        // --- Título Agregado ---
        Text(
            text = "Registro de usuario",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Cyan,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espacio después del título
        // --- Fin Título ---

        // Nombre
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre completo") }, // Actualizado según tu código
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre campos (reducido)

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

        Spacer(modifier = Modifier.height(8.dp)) // (reducido)

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

        Spacer(modifier = Modifier.height(8.dp)) // (reducido)

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

        Spacer(modifier = Modifier.height(8.dp)) // (reducido)

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

        Spacer(modifier = Modifier.height(8.dp)) // (reducido)

        // --- Campo Teléfono Agregado ---
        OutlinedTextField(
            value = estado.telefono,
            onValueChange = viewModel::onTelefonoChange,
            label = { Text("Teléfono") },
            isError = estado.errores.telefono != null,
            supportingText = {
                estado.errores.telefono?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )
        // --- Fin Campo Teléfono ---

        Spacer(modifier = Modifier.height(16.dp)) // Más espacio antes del checkbox

        // Checkbox de aceptación de términos
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() // Asegura que el Row ocupe el ancho
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = viewModel::onAceptarTerminosChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Texto clickeable para abrir el diálogo
            Text(
                text = "Acepto los términos y condiciones",
                modifier = Modifier.clickable { showTermsDialog = true },
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.Underline // Subrayado para indicar que es clickeable
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            // --- Modificador del Botón Ajustado ---
            modifier = Modifier.fillMaxWidth(0.8f), // Botón más angosto (80%)
            // --- Color del Botón Modificado ---
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Cyan
            )
        ) {
            Text("Registrar", color = Color.Black) // Color de texto para legibilidad
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio al final
    }

    // --- Diálogo de Términos y Condiciones ---
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false }, // Se cierra si se toca fuera
            title = { Text(text = "Términos y Condiciones") },
            text = {
                // Colocamos el texto en una columna scrollable por si es muy largo
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "Bienvenido a LEVEL-UP GAMER.\n\n" +
                                "1. Uso de la Aplicación:\n" +
                                "Al registrarte, aceptas usar esta aplicación de manera responsable y de acuerdo con todas las leyes aplicables. No utilizarás la aplicación para ningún propósito ilegal o no autorizado.\n\n" +
                                "2. Cuenta de Usuario:\n" +
                                "Eres responsable de mantener la confidencialidad de tu cuenta y contraseña. Aceptas la responsabilidad de todas las actividades que ocurran bajo tu cuenta.\n\n" +
                                "3. Privacidad:\n" +
                                "Nuestra política de privacidad describe cómo manejamos la información que nos proporcionas. Al usar la app, aceptas la recopilación y uso de esta información.\n\n" +
                                "4. Compras y Pagos:\n" +
                                "Todos los precios se indican en la moneda local. Nos reservamos el derecho de cambiar los precios en cualquier momento.\n\n" +
                                "5. Limitación de Responsabilidad:\n" +
                                "LEVEL-UP GAMER no será responsable de ningún daño que resulte del uso de esta aplicación.\n\n" +
                                "Al hacer clic en 'Aceptar', confirmas que has leído y entendido estos términos."
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showTermsDialog = false }, // Cierra el diálogo
                    // --- Color Agregado ---
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Cyan
                    )
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
    // --- Fin Diálogo ---
}