package com.example.productinfo.presentation.ui.products

sealed class ProductsEvent {
    data object OnLoading: ProductsEvent()
}