package com.example.productinfo.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.productinfo.data.converter.mapToProducts
import com.example.productinfo.data.models.Response
import com.example.productinfo.data.network.DummyApiService
import com.example.productinfo.domain.models.RequestParam
import com.example.productinfo.domain.repository.ProductsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: DummyApiService,
    @ApplicationContext private val context: Context
): ProductsRepository {
    override suspend fun getProducts(params: RequestParam): Response {
        if (!isConnected()) {
            return Response().apply { code = -1 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = api.getProducts(
                    params.skip,
                    params.limit
                ).mapToProducts()
                response.apply { code = 200 }
            } catch (e: SocketTimeoutException) {
                Log.d("TEST", "request timeout")
                Response().apply { code = 408 }
            } catch (e: Throwable) {
                Log.d("TEST", "error: ${e.message} ${e.stackTrace}")
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