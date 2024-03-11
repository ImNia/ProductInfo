package com.example.productinfo.data.repository

import android.content.Context
import com.example.productinfo.data.network.DummyApiService
import com.example.productinfo.data.network.dto.ProductsDto
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.repository.ProductsRepository
import com.example.productinfo.utils.ConnectedException
import com.example.productinfo.utils.isConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: DummyApiService,
    @ApplicationContext private val context: Context
) : ProductsRepository {
    override suspend fun getProducts(params: RequestParam): ProductsDto {
        if (!isConnected(context)) {
            throw ConnectedException()
        }

        return api.getProducts(
            params.skip,
            params.limit
        )
    }

    override suspend fun getCategories(): List<String> {
        if (!isConnected(context)) {
            throw ConnectedException()
        }

        return api.getCategories()
    }

    override suspend fun getProductsByCategory(
        category: String,
        params: RequestParam
    ): ProductsDto {
        if (!isConnected(context)) {
            throw ConnectedException()
        }

        return api.getProductByCategory(
            category,
            params.skip,
            params.limit,
        )
    }

    override suspend fun search(query: String, params: RequestParam): ProductsDto {
        if (!isConnected(context)) {
            throw ConnectedException()
        }

        return api.search(
            query,
            params.skip,
            params.limit,
        )
    }
}