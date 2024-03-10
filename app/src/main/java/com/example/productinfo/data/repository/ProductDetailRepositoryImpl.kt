package com.example.productinfo.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.productinfo.data.converter.mapToProduct
import com.example.productinfo.data.models.Response
import com.example.productinfo.data.network.DummyApiService
import com.example.productinfo.domain.repository.ProductDetailRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val api: DummyApiService,
    @ApplicationContext private val context: Context
): ProductDetailRepository {
    override suspend fun getProductDetail(productId: String): Response {
        if (!isConnected()) {
            return Response().apply { code = -1 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = api.getProductDetail(
                    productId
                ).mapToProduct()
                response.apply { code = 200 }
            } catch (e: SocketTimeoutException) {
                Response().apply { code = 408 }
            } catch (e: Throwable) {
                Response().apply { code = 500 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}