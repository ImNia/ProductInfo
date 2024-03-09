package com.example.productinfo.data.network

import com.example.productinfo.data.network.dto.ProductsDto
import retrofit2.http.GET

interface DummyApiService {
    @GET("products/")
    suspend fun getProducts(): ProductsDto
}