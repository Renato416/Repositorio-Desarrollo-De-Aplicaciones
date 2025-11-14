package com.example.level_up.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up.ui.Screen.*
import com.example.level_up.viewmodel.CartViewModel
import com.example.level_up.viewmodel.ProductViewModel
import com.example.level_up.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    cartViewModel: CartViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel() // <-- Este ViewModel sigue aquí, ¡y es necesario!
) {
    NavHost(
        navController = navController,
        startDestination = "login" // pantalla inicial
    ) {

        // --- Pantalla de Login (NUEVA ARQUITECTURA) ---
        composable("login") {
            // Llama a la nueva LoginScreen pasándole las acciones de navegación
            LoginScreen(
                onRegisterClick = {
                    // Cuando el usuario haga clic en "Registrate", navega a la pantalla de registro
                    navController.navigate("registro")
                },
                onLoginSuccess = {
                    // Cuando el login sea exitoso, navega a "home" y limpia la pila
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true } // Borra el login del historial
                        launchSingleTop = true // Evita duplicar la pantalla "home"
                    }
                }
            )
        }

        // --- Pantalla de Registro (ARQUITECTURA ANTIGUA) ---
        composable("registro") {
            // RegistroScreen sigue usando el UsuarioViewModel, ¡lo cual está perfecto!
            RegistroScreen(navController, usuarioViewModel)
        }

        // Pantallas de tienda
        composable("home") {
            HomeScreen(
                navController = navController,
                productViewModel = productViewModel,
                cartViewModel = cartViewModel
            )
        }
        composable("cart") {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}