package com.example.productinfo.presentation.ui.product

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProductDetailScreen(
    productDetailScreenavController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    Text(text = "Details")
}