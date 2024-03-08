package com.example.productinfo.presentation.navigation

sealed class NavigationDestination(val destination: String) {
    object ProductsScreen: NavigationDestination("productsScreen")
}