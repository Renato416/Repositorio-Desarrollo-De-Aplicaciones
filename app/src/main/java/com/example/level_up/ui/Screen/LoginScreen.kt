package com.example.level_up.ui.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.data.local.AppDatabase
import com.example.level_up.repository.AuthRepository
import com.example.level_up.data.util.Result
import com.example.level_up.viewmodel.LoginViewModel
import com.example.level_up.viewmodel.LoginViewModelFactory

/**
 * Esta es la pantalla de Login completa que usa la nueva arquitectura
 */
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    // Obtenemos la base de datos y el DAO
    val userDao = AppDatabase.getDatabase(context).usuarioDao()
    // Creamos el Repositorio
    val repository = remember { AuthRepository(userDao) }
    // Creamos el ViewModel usando la Fábrica
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2937)) // fondo gris azulado
            .padding(16.dp)
    ) {
        Login(
            modifier = Modifier.align(Alignment.Center),
            loginViewModel = loginViewModel,
            onRegisterClick = onRegisterClick,
            onLoginSuccess = onLoginSuccess
        )
    }
}

/**
 * Este es el Composable interno que contiene la UI
 */
@Composable
fun Login(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Estos estados son locales para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observamos el estado del login desde el ViewModel
    val loginState by loginViewModel.loginState.collectAsState()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderTitle(Modifier.align(Alignment.CenterHorizontally)) // Título que reemplaza la imagen
        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                loginViewModel.onEmailChange(it) // Actualizamos el ViewModel
            },
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.colors( // Colores para el tema oscuro
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginViewModel.onPasswordChange(it) // Actualizamos el ViewModel
            },
            placeholder = { Text(text = "Contraseña") },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors( // Colores para el tema oscuro
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón Login
        Button(
            onClick = { loginViewModel.login() }, // El ViewModel maneja el click
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)), // Color verde
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Iniciar Sesión", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Registro
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                "¿No tienes una cuenta?, ",
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                "Registrate",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF22C55E), // Color verde
                modifier = Modifier.clickable { onRegisterClick() } // Navega al registro
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Estado login (Loading, Error, Success)
        loginState?.let { state ->
            when (state) {
                is Result.Loading -> {
                    // Muestra un indicador de carga
                    CircularProgressIndicator(color = Color(0xFF22C55E))
                    Log.d("LoginScreen", "Login state: Loading...")
                }
                is Result.Success -> {
                    // Navegación exitosa
                    Log.d("LoginScreen", "Login state: Success! Navigating home...")
                    onLoginSuccess()
                    loginViewModel.resetState()
                }
                is Result.Error -> {
                    // Muestra el mensaje de error
                    Log.d("LoginScreen", "Login state: Error: ${state.message}")
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

/**
 * Reemplazo de la imagen R.drawable.logolevelupgamer
 */
@Composable
fun HeaderTitle(modifier: Modifier) {
    Text(
        text = "LEVEL-UP GAMER",
        color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}