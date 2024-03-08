package com.example.productinfo.data.repository

import com.example.productinfo.data.network.DummyApiService
import com.example.productinfo.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: DummyApiService
): ProductsRepository {

}