package com.example.level_up.model


data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val rating: Float = 0f,
    val reviews: MutableList<Review> = mutableListOf()
)

