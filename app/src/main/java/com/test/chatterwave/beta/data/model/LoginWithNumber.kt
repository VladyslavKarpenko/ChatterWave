package com.test.chatterwave.beta.data.model

import com.google.gson.annotations.SerializedName

data class LoginWithNumber(
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("password")
    val password: String?
)
