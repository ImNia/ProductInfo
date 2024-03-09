package com.example.productinfo.domain.usecases

import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.Products
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.repository.ProductsRepository
import com.example.productinfo.utils.Resource
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun getProducts(params: RequestParam): Resource<Products> {
        val response = repository.getProducts(params)

        return when(response.code) {
            -1 -> {
                Resource.Error(ErrorType.NOT_CONNECT)
            }
            200 -> {
                with(response as Products) {
                    Resource.Success(this)
                }
            }
            else -> {
                Resource.Error(ErrorType.SERVER_ERROR)
            }
        }
    }
}