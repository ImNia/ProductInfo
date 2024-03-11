package com.example.productinfo.data.repository

import android.content.Context
import com.example.productinfo.data.network.DummyApiService
import com.example.productinfo.data.network.dto.ProductDto
import com.example.productinfo.domain.repository.ProductDetailRepository
import com.example.productinfo.utils.ConnectedException
import com.example.productinfo.utils.isConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val api: DummyApiService,
    @ApplicationContext private val context: Context
): ProductDetailRepository {
    override suspend fun getProductDetail(productId: String): ProductDto {
        if (!isConnected(context)) {
            throw ConnectedException()
        }

        return api.getProductDetail(productId)
    }
}