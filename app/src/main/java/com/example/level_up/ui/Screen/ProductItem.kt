package com.example.level_up.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up.model.Product
import com.example.level_up.model.Review

@Composable
fun ProductItem(
    product: Product,
    onAddToCart: () -> Unit,
    onAddReview: (Review) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(product.name, color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("$${"%.3f".format(product.price)}", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar rating promedio
            Text(
                text = "Calificación: ${"%.1f".format(product.rating)} / 5",
                color = Color.Yellow,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onAddToCart() },
                    colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
                ) {
                    Text("Añadir", color = Color.White)
                }

                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
                ) {
                    Text("Reseña", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 100.dp)
            ) {
                items(product.reviews) { review ->
                    Text(
                        "- ${review.username} (${review.rating}/5): ${review.comment}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddReviewDialog(
            onSubmit = { rating, comment ->
                onAddReview(Review(username = "Usuario", rating = rating, comment = comment))
            },
            onDismiss = { showDialog = false }
        )
    }
}
