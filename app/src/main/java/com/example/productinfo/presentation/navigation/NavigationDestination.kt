package com.example.productinfo.presentation.navigation

sealed class NavigationDestination(val destination: String) {
    data object ProductsScreen: NavigationDestination("productsScreen")
    data object ProductDetailScreen: NavigationDestination("productDetailScreen/{productId}")
}