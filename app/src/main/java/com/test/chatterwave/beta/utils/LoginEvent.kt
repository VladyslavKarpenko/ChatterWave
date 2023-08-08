package com.test.chatterwave.beta.utils

sealed class LoginEvent(

    val email: String? = null,
    val phone: String? = null,
    val password: String? = null

){

    class LoginWithEmail(email: String, password: String) : LoginEvent(email = email, password = password)
    class LoginWithPhone(phone: String, password: String) : LoginEvent(phone =phone, password = password)

}
