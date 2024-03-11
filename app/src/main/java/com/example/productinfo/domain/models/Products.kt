package com.example.productinfo.domain.models

data class Products(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)
