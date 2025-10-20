package com.example.level_up.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up.repository.ProductRepository

@Composable
fun ProductListScreen() {
    val products = ProductRepository.getProducts()

    Column(modifier = Modifier.background(Color.Black)) {
        Text(
            text = "PRODUCTOS",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp)
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}
