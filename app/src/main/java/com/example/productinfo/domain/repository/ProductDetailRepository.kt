package com.example.productinfo.domain.repository

import com.example.productinfo.data.models.Response

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: String): Response
}