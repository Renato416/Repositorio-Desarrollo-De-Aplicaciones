package com.example.level_up.repository

import com.example.level_up.R
import com.example.level_up.model.Product

object ProductRepository {
    fun getProducts() = listOf(
        Product(1, "Audífonos GAMER", 20990.0, R.drawable.audifonos),
        Product(2, "Silla GAMER", 49990.0, R.drawable.silla),
        Product(3, "Mouse GAMER", 14990.0, R.drawable.mouse),
        Product(4, "Teclado GAMER", 30990.0, R.drawable.teclado),
        Product(5, "Pantalla GAMER", 90990.0, R.drawable.pantalla),
        Product(6, "Mando GAMER", 25990.0, R.drawable.mando)
    )
}
