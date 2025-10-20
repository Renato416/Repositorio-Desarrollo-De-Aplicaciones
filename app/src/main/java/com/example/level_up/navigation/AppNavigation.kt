package com.example.level_up.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up.ui.Screen.CartScreen
import com.example.level_up.ui.Screen.HomeScreen
import com.example.level_up.ui.RegistroScreen
import com.example.level_up.ui.ResumenScreen
import com.example.level_up.viewmodel.CartViewModel
import com.example.level_up.viewmodel.UsuarioViewModel
import androidx.navigation.NavHostController

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    // ViewModels compartidos
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        // Registro y resumen de usuario
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }
        composable("resumen") {
            ResumenScreen(usuarioViewModel)
        }

        // Tienda y carrito
        composable("home") {
            HomeScreen(navController, cartViewModel)
        }
        composable("cart") {
            CartScreen(navController, cartViewModel)
        }
    }
}
