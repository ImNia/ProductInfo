package com.example.productinfo.utils

import com.example.productinfo.domain.models.ErrorType

sealed class Resource<T>(val data: T? = null, val error: ErrorType? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: ErrorType, data: T? = null) : Resource<T>(data, error)
}