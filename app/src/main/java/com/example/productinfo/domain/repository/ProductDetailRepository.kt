package com.example.productinfo.domain.repository

import com.example.productinfo.data.network.dto.ProductDto

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: String): ProductDto
}