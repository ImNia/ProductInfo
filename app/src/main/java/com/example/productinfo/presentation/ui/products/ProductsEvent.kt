package com.example.productinfo.presentation.ui.products

sealed class ProductsEvent {
    data object OnLoading: ProductsEvent()
    data object OnHideAlert: ProductsEvent()
    data class OnCategorySelect(
        val category: String,
    ): ProductsEvent()

    data class OnSearch(
        val query: String,
    ): ProductsEvent()
}