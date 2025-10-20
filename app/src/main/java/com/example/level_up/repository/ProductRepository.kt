package com.example.level_up.repository



import com.example.level_up.R
import com.example.level_up.model.Product

object ProductRepository {
    fun getProducts() = listOf(
        Product(1, "Audífonos GAMER", 64990.0, R.drawable.audifonos),
        Product(2, "Silla GAMER", 72990.0, R.drawable.silla)
    )
}
