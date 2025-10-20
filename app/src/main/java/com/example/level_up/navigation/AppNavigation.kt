package com.example.level_up.navigation
/*AppNavigation.kt*/
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.viewmodel.UsuarioViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up.ui.LoginScreen
import com.example.level_up.ui.RegistroScreen
import com.example.level_up.ui.ResumenScreen

@Composable
fun AppNavigation() {
    /*val navController = rememberNavController()*/
    val navController = rememberNavController()

    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login" // la pantalla inicial
    ) {
        composable("login") {
            LoginScreen(navController) // aquí va tu pantalla de bienvenida
        }
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }
        composable("resumen") {
            ResumenScreen(usuarioViewModel)
        }
    }
}