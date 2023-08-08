package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class ChangePasswordDomainResponseModel(
    @SerializedName("error")
    val error: String
)