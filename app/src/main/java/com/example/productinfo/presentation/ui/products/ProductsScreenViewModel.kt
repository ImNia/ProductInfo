package com.example.productinfo.presentation.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.usecases.ProductsUseCase
import com.example.productinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
) : ViewModel() {
    private var _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    init {
        _state.update { productsState ->
            productsState.copy(
                isLoading = true,
                error = null,
                existError = false
            )
        }
        getProductsData()
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            ProductsEvent.OnLoading -> {
                _state.update { productsState ->
                    productsState.copy(
                        isLoading = true,
                        error = null,
                        existError = false
                    )
                }
                getProductsData()
            }

            ProductsEvent.OnHideAlert -> {
                _state.update { productsState ->
                    productsState.copy(
                        error = null,
                        isLoading = false,
                        existError = true,
                    )
                }
            }
        }
    }

    private fun getProductsData() {
        viewModelScope.launch {
            if (!isEndScreen()) {
                val productsData = productsUseCase.getProducts(
                    getParams()
                )

                when (productsData) {
                    is Resource.Success -> {
                        _state.update { productsState ->
                            productsState.copy(
                                productData = productsData.data,
                                products = state.value.products + productsData.data!!.products,
                                isLoading = false,
                                existError = false,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { productsState ->
                            productsState.copy(
                                error = productsData.error ?: ErrorType.UNKNOWN,
                                existError = true,
                            )
                        }
                    }
                }
            } else {
                _state.update { productsState ->
                    productsState.copy(
                        isLoading = false
                    )
                }
            }
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
        return if (state.value.productData == null) {
            false
        } else {
            state.value.productData!!.total <= (state.value.productData!!.skip + LIMIT_DATA)
        }
    }

    companion object {
        const val LIMIT_DATA = 20
    }
}