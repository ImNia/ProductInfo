package com.example.productinfo.di

import com.example.productinfo.data.repository.ProductsRepositoryImpl
import com.example.productinfo.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductsRepository(repositoryImpl: ProductsRepositoryImpl): ProductsRepository
}