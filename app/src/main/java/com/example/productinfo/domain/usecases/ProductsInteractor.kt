package com.example.productinfo.domain.usecases

import com.example.productinfo.domain.models.Categories
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.domain.models.Products
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.repository.ProductsRepository
import com.example.productinfo.utils.Resource
import javax.inject.Inject

class ProductsInteractor @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun getProducts(category: String?, params: RequestParam): Resource<Products> {
        val response = if (category == null) repository.getProducts(params)
            else repository.getProductsByCategory(category, params)

        return when (response.code) {
            -1 -> {
                Resource.Error(ErrorType.NOT_CONNECT)
            }

            408 -> {
                Resource.Error(ErrorType.REQUEST_TIMEOUT)
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

    suspend fun getCategories(): Resource<Categories> {
        val response = repository.getCategories()

        return when (response.code) {
            -1 -> {
                Resource.Error(ErrorType.NOT_CONNECT)
            }

            408 -> {
                Resource.Error(ErrorType.REQUEST_TIMEOUT)
            }

            200 -> {
                with(response as Categories) {
                    Resource.Success(this)
                }
            }

            else -> {
                Resource.Error(ErrorType.SERVER_ERROR)
            }
        }
    }
}