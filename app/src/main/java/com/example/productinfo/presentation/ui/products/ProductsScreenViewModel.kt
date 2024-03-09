package com.example.productinfo.presentation.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productinfo.domain.usecases.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
): ViewModel() {
    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val products = productsUseCase.getProducts()

            Log.d("TEST", "products: ${products.data}")
        }
    }
}