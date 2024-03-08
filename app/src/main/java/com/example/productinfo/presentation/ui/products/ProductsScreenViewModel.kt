package com.example.productinfo.presentation.ui.products

import androidx.lifecycle.ViewModel
import com.example.productinfo.domain.usecases.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
): ViewModel() {
}