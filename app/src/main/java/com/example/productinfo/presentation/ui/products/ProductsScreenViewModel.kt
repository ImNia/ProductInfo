package com.example.productinfo.presentation.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.usecases.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
): ViewModel() {
    private var _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    init {
        getProductsData()
    }

    fun onEvent(event: ProductsEvent) {
        when(event) {
            ProductsEvent.OnLoading -> {
                _state.update { productsState ->
                    productsState.copy(
                        isLoading = true
                    )
                }
                getProductsData()
            }
        }
    }

    private fun getProductsData() {
        viewModelScope.launch {
            if(!isEndScreen()) {
                val productsData = productsUseCase.getProducts(
                    getParams()
                )

                _state.update { productsState ->
                    productsState.copy(
                        productData = productsData.data,
                        products = state.value.products + productsData.data!!.products,
                        isLoading = false
                    )
                }
            }

            Log.d("TEST", "load: ${state.value.products}")
        }
    }

    private fun getParams(): RequestParam {
        return if (state.value.productData == null) {
            RequestParam(
                skip = 0,
                limit = LIMIT_DATA
            )
        } else {
            state.value.productData?.let {
                RequestParam(
                    skip = state.value.productData!!.skip + LIMIT_DATA,
                    limit = LIMIT_DATA
                )
            } ?: throw Throwable("Sometimes went wrong")
        }
    }

    private fun isEndScreen(): Boolean {
        return if(state.value.productData == null) {
            false
        } else {
            state.value.productData!!.total <= (state.value.productData!!.skip + LIMIT_DATA)
        }
    }

    companion object {
        const val LIMIT_DATA = 20
    }
}