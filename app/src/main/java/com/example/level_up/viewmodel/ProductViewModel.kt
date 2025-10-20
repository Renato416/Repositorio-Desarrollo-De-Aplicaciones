package com.example.level_up.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.level_up.model.Product
import com.example.level_up.model.Review
import com.example.level_up.repository.ProductRepository

class ProductViewModel : ViewModel() {

    private val _products = mutableStateListOf<Product>()
    val products: List<Product> get() = _products

    init {
        _products.addAll(ProductRepository.getProducts())
    }

    fun addToCart(product: Product) {
        // Funcionalidad de carrito si se desea integrar aquí
    }

    fun addReview(productId: Int, review: Review) {
        val index = _products.indexOfFirst { it.id == productId }
        if (index != -1) {
            val product = _products[index]
            product.reviews.add(review)

            // Recalcular rating promedio
            val avgRating = if (product.reviews.isEmpty()) 0f
            else product.reviews.map { it.rating }.average().toFloat()

            _products[index] = product.copy(
                reviews = product.reviews,
                rating = avgRating
            )
        }
    }
}
