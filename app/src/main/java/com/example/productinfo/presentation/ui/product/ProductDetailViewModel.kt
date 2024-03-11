package com.example.productinfo.presentation.ui.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productinfo.domain.usecases.ProductDetailInteractor
import com.example.productinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: ProductDetailInteractor,
): ViewModel() {

    private var _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()
    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val productId = checkNotNull(savedStateHandle["productId"]).toString()
            when (val data = interactor.getProductDetail(productId)) {
                is Resource.Success -> {
                    _state.update { productDetailState ->
                        productDetailState.copy(
                            isLoading = false,
                            product = data.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { productDetailState ->
                        productDetailState.copy(
                            error = data.error
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            ProductDetailEvent.OnLoading -> {
                _state.update { productsState ->
                    productsState.copy(
                        isLoading = true,
                        error = null,
                    )
                }
                getData()
            }

            ProductDetailEvent.OnHideAlert -> {
                _state.update { productsState ->
                    productsState.copy(
                        error = null,
                        isLoading = false,
                    )
                }
            }
        }
    }
}