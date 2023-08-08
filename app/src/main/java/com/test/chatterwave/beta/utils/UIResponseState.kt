package com.test.chatterwave.beta.utils

sealed class UIResponseState {
    object Loading: UIResponseState()
    data class Error(val errorMessage: String): UIResponseState()
    data class Success<T>(val content: T): UIResponseState()
    object Empty: UIResponseState()
}
