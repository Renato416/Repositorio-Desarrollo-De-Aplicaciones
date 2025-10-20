package com.example.level_up.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.level_up.model.Product

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<Product>()
    val cartItems: List<Product> get() = _cartItems

    fun addToCart(product: Product) {
        _cartItems.add(product)
    }

    fun removeFromCart(product: Product) {
        _cartItems.remove(product)
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.price }
    }
}