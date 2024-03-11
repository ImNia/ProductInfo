package com.example.productinfo.domain.usecases

import com.example.productinfo.data.converter.mapToProducts
import com.example.productinfo.domain.models.Categories
import com.example.productinfo.domain.models.Products
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.repository.ProductsRepository
import com.example.productinfo.utils.Resource
import javax.inject.Inject

class ProductsInteractor @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun getProducts(category: String?, params: RequestParam): Resource<Products> {
        return Resource.handleResponse {
            if (category == null) repository.getProducts(params).mapToProducts()
                else repository.getProductsByCategory(category, params).mapToProducts()
        }
    }

    suspend fun getCategories(): Resource<Categories> {
        return Resource.handleResponse {
            Categories(
                categories = repository.getCategories()
            )
        }
    }

    suspend fun search(query: String, params: RequestParam): Resource<Products> {
        return Resource.handleResponse {
            repository.search(query, params).mapToProducts()
        }
    }
}