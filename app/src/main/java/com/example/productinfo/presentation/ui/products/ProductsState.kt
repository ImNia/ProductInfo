package com.example.productinfo.presentation.ui.products

import com.example.productinfo.domain.models.Product
import com.example.productinfo.domain.models.Products

data class ProductsState(
    val productData: Products? = null,
    val products: List<Product> = listOf(),
    val isLoading: Boolean = false,
)