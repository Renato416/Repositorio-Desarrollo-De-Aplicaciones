package com.example.level_up


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.level_up.ui.Screen.CartScreen
import com.example.level_up.ui.Screen.HomeScreen
import com.example.level_up.viewmodel.CartViewModel

@Composable
fun AppNavHost(navController: NavHostController, cartViewModel: CartViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, cartViewModel) }
        composable("cart") { CartScreen(navController, cartViewModel) }
    }
}
