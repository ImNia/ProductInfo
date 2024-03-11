package com.example.productinfo.presentation.ui.products

import com.example.productinfo.domain.models.Categories
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.Product
import com.example.productinfo.domain.models.Products

data class ProductsState(
    val productData: Products? = null,
    val products: List<Product> = listOf(),
    val isLoading: Boolean = false,
    val error: ErrorType? = null,
    val existError: Boolean = false,
    val categories: Categories? = null,
)