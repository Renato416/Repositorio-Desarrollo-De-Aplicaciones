package com.example.level_up


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.level_up.ui.Screen.CartScreen
import com.example.level_up.ui.Screen.HomeScreen
import com.example.level_up.viewmodel.CartViewModel
import com.example.level_up.viewmodel.ProductViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
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
