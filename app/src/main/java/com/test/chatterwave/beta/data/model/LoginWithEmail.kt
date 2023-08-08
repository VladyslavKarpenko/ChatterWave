package com.test.chatterwave.beta.data.model

import com.google.gson.annotations.SerializedName

data class LoginWithEmail(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)