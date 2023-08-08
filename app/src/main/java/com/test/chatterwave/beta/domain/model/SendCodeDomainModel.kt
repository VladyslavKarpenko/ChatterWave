package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class SendCodeDomainModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)