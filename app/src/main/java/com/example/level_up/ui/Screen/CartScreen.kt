package com.example.level_up.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
// import androidx.compose.material.icons.Icons // <-- Comentado temporalmente
// import androidx.compose.material.icons.filled.ArrowBack // <-- Comentado temporalmente
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level_up.model.Product
import com.example.level_up.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
                // --- SOLUCIÓN TEMPORAL ---
                // Se comenta el navigationIcon para evitar el error de 'Icons.Filled.ArrowBack'
                // Esto es porque nos falta la librería 'material-icons-extended'
                /*
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                */
                // --- FIN SOLUCIÓN TEMPORAL ---
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartViewModel.cartItems) { product ->
                    CartItem(product = product, onRemove = { cartViewModel.removeFromCart(product) })
                }
            }

            Text(
                text = "Total: $${"%.2f".format(cartViewModel.getTotalPrice())}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun CartItem(product: Product, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(product.name)
            Button(onClick = onRemove) {
                Text("Eliminar")
            }
        }
    }
}