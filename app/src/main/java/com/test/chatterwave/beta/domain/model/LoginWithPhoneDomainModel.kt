package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class LoginWithPhoneDomainModel(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)