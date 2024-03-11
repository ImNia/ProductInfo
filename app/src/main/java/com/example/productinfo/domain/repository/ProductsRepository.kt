package com.example.productinfo.domain.repository

import com.example.productinfo.data.network.dto.ProductsDto
import com.example.productinfo.domain.models.RequestParam

interface ProductsRepository {
    suspend fun getProducts(params: RequestParam): ProductsDto
    suspend fun getCategories(): List<String>
    suspend fun getProductsByCategory(category: String, params: RequestParam): ProductsDto
    suspend fun search(query: String, params: RequestParam): ProductsDto
}