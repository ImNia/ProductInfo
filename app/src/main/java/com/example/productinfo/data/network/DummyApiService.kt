package com.example.productinfo.data.network

import com.example.productinfo.data.network.dto.ProductDto
import com.example.productinfo.data.network.dto.ProductsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApiService {
    @GET("products/")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): ProductsDto

    @GET("products/{productId}")
    suspend fun getProductDetail(
        @Path("productId") productId: String,
    ): ProductDto
}