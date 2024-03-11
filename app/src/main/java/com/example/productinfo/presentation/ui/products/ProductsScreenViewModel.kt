package com.example.productinfo.presentation.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.usecases.ProductsInteractor
import com.example.productinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor
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
        getCategories()
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
                getProductsData(state.value.selectedCategories)
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

            is ProductsEvent.OnCategorySelect -> {
                val category =
                    if (state.value.selectedCategories?.trim() == event.category.trim()) null else event.category

                _state.update { productsState ->
                    productsState.copy(
                        loadData = null,
                        products = listOf(),
                        selectedCategories = category
                    )
                }
                getProductsData(category)
            }

            is ProductsEvent.OnSearch -> {
                _state.update { productsState ->
                    productsState.copy(
                        isLoading = true,
                        loadData = null,
                        products = listOf(),
                        error = null,
                        existError = false,
                        selectedCategories = null,
                        query = event.query.trim()
                    )
                }
                search(event.query.trim())
            }
        }
    }

    private fun getProductsData(category: String? = null) {
        viewModelScope.launch {
            if (!isEndScreen()) {
                val productsData = productsInteractor.getProducts(
                    category,
                    getParams()
                )

                when (productsData) {
                    is Resource.Success -> {
                        _state.update { productsState ->
                            productsState.copy(
                                loadData = productsData.data,
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

    private fun search(query: String) {
        viewModelScope.launch {
            if (!isEndScreen()) {
                val productsData = productsInteractor.search(
                    query,
                    getParams()
                )

                when (productsData) {
                    is Resource.Success -> {
                        _state.update { productsState ->
                            productsState.copy(
                                loadData = productsData.data,
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

    private fun getCategories() {
        viewModelScope.launch {
            val data = productsInteractor.getCategories()

            _state.update { productsState ->
                productsState.copy(
                    categories = data.data
                )
            }
        }
    }

    private fun getParams(): RequestParam {
        return if (state.value.loadData == null || state.value.loadData?.skip == 0) {
            RequestParam(
                skip = 0,
                limit = LIMIT_DATA
            )
        } else {
            state.value.loadData?.let {
                RequestParam(
                    skip = state.value.loadData!!.skip + LIMIT_DATA,
                    limit = LIMIT_DATA
                )
            } ?: throw Throwable("Sometimes went wrong")
        }
    }

    private fun isEndScreen(): Boolean {
        return if (state.value.loadData == null) {
            false
        } else {
            state.value.loadData!!.total <= (state.value.loadData!!.skip + LIMIT_DATA)
        }
    }

    companion object {
        const val LIMIT_DATA = 20
    }
}