package com.example.productinfo.data.network

import com.example.productinfo.data.network.dto.ProductsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApiService {
    @GET("products/")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): ProductsDto
}