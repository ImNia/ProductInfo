package com.example.productinfo.presentation.ui.product

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val productId = checkNotNull(savedStateHandle["productId"]).toString()
            Log.d("TEST", "catch data: $productId")
        }
    }
}