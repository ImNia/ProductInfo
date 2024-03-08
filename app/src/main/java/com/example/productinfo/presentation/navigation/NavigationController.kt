package com.example.productinfo.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    }
}