package com.example.productinfo.data.converter

import com.example.productinfo.data.network.dto.ProductDto
import com.example.productinfo.data.network.dto.ProductsDto
import com.example.productinfo.domain.models.Product
import com.example.productinfo.domain.models.Products

fun ProductsDto.mapToProducts(): Products {
    return Products(
        products = this.products.map {
            it.mapToProduct()
        },
        total = this.total,
        skip = this.skip,
        limit = this.limit
    )
}

fun ProductDto.mapToProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        stock = this.stock,
        brand = this.brand,
        category = this.category,
        thumbnail = this.thumbnail,
        images = this.images
    )
}