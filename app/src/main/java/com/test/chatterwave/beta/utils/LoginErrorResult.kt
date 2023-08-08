package com.test.chatterwave.beta.utils

sealed class LoginErrorResult{

    object LoginPhoneEmailError: LoginErrorResult()
    object LoginPasswordError: LoginErrorResult()
    object LoginUnknownError: LoginErrorResult()

}
