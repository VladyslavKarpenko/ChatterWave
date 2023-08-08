package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class ConfirmCodeErrorDomainResponseModel(
    @SerializedName("error")
    val error: String
)