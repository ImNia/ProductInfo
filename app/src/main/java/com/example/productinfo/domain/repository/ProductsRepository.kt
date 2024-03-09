package com.example.productinfo.domain.repository

import com.example.productinfo.data.models.Response

interface ProductsRepository {
    suspend fun getProducts(dto: Any): Response
}