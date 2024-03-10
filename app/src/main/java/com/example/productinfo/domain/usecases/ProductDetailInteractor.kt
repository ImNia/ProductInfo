package com.example.productinfo.domain.usecases

import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.Product
import com.example.productinfo.domain.repository.ProductDetailRepository
import com.example.productinfo.utils.Resource
import javax.inject.Inject

class ProductDetailInteractor @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend fun getProductDetail(productId: String): Resource<Product> {
        val response = repository.getProductDetail(productId)

        return when(response.code) {
            -1 -> {
                Resource.Error(ErrorType.NOT_CONNECT)
            }
            408 -> {
                Resource.Error(ErrorType.REQUEST_TIMEOUT)
            }
            200 -> {
                with(response as Product) {
                    Resource.Success(this)
                }
            }
            else -> {
                Resource.Error(ErrorType.SERVER_ERROR)
            }
        }
    }
}