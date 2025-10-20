package com.example.level_up.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.level_up.repository.ProductRepository
import com.example.level_up.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val products = ProductRepository.getProducts()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tienda LevelUp", color = Color.White) },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("cart")
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2196F3))
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar producto", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF2196F3),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF2196F3),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Text(
                text = "PRODUCTOS",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )

            val filteredProducts = products.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductItem(product = product) {
                        cartViewModel.addToCart(product)
                    }
                }
            }
        }
    }
}
