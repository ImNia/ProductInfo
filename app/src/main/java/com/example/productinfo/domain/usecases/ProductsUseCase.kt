package com.example.productinfo.domain.usecases

import android.util.Log
import com.example.productinfo.domain.models.Products
import com.example.productinfo.domain.repository.ProductsRepository
import com.example.productinfo.utils.Resource
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun getProducts(): Resource<Products> {
        val response = repository.getProducts(Unit)

        Log.d("TEST", "response: ${response.code}")
        return when(response.code) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as Products) {
                    Resource.Success(this)
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}