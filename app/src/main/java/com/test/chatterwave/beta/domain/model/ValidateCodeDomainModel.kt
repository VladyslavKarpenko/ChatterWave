package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class ValidateCodeDomainModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)