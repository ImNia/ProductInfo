package com.example.productinfo.domain.usecases

import com.example.productinfo.data.converter.mapToProduct
import com.example.productinfo.domain.models.Product
import com.example.productinfo.domain.repository.ProductDetailRepository
import com.example.productinfo.utils.Resource
import javax.inject.Inject

class ProductDetailInteractor @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend fun getProductDetail(productId: String): Resource<Product> {
        return Resource.handleResponse {
            repository.getProductDetail(productId).mapToProduct()
        }
    }
}