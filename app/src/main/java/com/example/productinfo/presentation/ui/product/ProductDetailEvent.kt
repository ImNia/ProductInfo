package com.example.productinfo.presentation.ui.product

sealed class ProductDetailEvent {
    data object OnLoading: ProductDetailEvent()
}