package com.example.productinfo.presentation.ui.product

import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val error: ErrorType? = null,
    val product: Product? = null,
)
