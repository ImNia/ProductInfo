package com.example.productinfo.domain.usecases

import com.example.productinfo.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
}