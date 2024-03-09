package com.example.productinfo.domain.models

import com.example.productinfo.data.models.Response

data class Products(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int,
): Response()
