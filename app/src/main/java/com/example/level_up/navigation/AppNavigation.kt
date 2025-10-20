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
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "login" // pantalla inicial
    ) {

        // Pantallas de usuario
        composable("login") {
            LoginScreen(navController)
        }
        composable("registro") {
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
