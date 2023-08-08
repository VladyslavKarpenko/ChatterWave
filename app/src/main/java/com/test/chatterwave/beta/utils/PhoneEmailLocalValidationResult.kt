package com.test.chatterwave.beta.utils

sealed class PhoneEmailLocalValidationResult(

    val message: Int? = null

) {

    class Success : PhoneEmailLocalValidationResult()
    class Error(message: Int?) : PhoneEmailLocalValidationResult(message = message)
    class Empty : PhoneEmailLocalValidationResult()

}
