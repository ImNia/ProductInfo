package com.example.productinfo.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.productinfo.presentation.ui.product.ProductDetailScreen
import com.example.productinfo.presentation.ui.products.ProductsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationController(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationDestination.ProductsScreen.destination) {
            ProductsScreen(navController)
        }

        composable(
            NavigationDestination.ProductDetailScreen.destination,
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
                nullable = false
            })
        ) {
            ProductDetailScreen(navController)
        }
    }
}