package com.example.productinfo.di

import com.example.productinfo.data.network.DummyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideBaseUrl() : String = "https://dummyjson.com/"


    @Provides
    @Singleton
    fun provideRetrofitClient(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDummyService(retrofit: Retrofit): DummyApiService {
        return retrofit.create(DummyApiService::class.java)
    }
}