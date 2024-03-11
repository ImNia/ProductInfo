package com.example.productinfo.data.network.dto

import com.google.gson.annotations.SerializedName

data class ProductsDto(
    @SerializedName("products")
    val products: List<ProductDto>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("limit")
    val limit: Int,
)
