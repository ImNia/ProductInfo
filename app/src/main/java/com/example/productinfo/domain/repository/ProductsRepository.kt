package com.example.productinfo.domain.repository

import com.example.productinfo.data.models.Response
import com.example.productinfo.domain.models.RequestParam

interface ProductsRepository {
    suspend fun getProducts(params: RequestParam): Response
    suspend fun getCategories(): Response
}