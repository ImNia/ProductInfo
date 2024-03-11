package com.example.productinfo.utils

import android.util.Log
import com.example.productinfo.domain.models.ErrorType
import retrofit2.HttpException
import java.io.IOException

sealed class Resource<T>(val data: T? = null, val error: ErrorType? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: ErrorType, data: T? = null) : Resource<T>(data, error)

    companion object {
        suspend fun <T> handleResponse(data: suspend () -> T): Resource<T> {
            return try {
                Success(data())
            } catch (e: ConnectedException) {
                Error(ErrorType.NOT_CONNECT)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("TEST", "code: ${e.message} ${e.stackTrace}")
                Error(ErrorType.UNKNOWN)
            } catch (e: HttpException) {
                when (e.code()) {
                    408 -> {
                        e.printStackTrace()
                        Error(ErrorType.REQUEST_TIMEOUT)
                    }

                    else -> {
                        e.printStackTrace()
                        Error(ErrorType.SERVER_ERROR)
                    }
                }
            }
                /*
            return when (response.code) {
                -1 -> {
                    Resource.Error(ErrorType.NOT_CONNECT)
                }

                408 -> {
                    Resource.Error(ErrorType.REQUEST_TIMEOUT)
                }

                200 -> {
                    Resource.Success(data)
                }

                else -> {
                    Resource.Error(ErrorType.SERVER_ERROR)
                }
            }*/
        }
    }
}