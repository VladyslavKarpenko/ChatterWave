package com.test.chatterwave.beta.utils

sealed class NetworkResult<T>(

    val data: T? = null,
    val message: Int? = null,
    val exception: String? = null

) {

    class Success<T>(data: T?) : NetworkResult<T>(data = data)
    class Error<T>(message: Int?) : NetworkResult<T>(message = message)
    class Exception<T>(exception: String?): NetworkResult<T>(exception = exception)
    class Loading<T> : NetworkResult<T>()
    class Empty<T>(message: Int?) : NetworkResult<T>(message = message)

}

