package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class ChangePasswordDataResponseModel(
    @SerializedName("message")
    val error: String?
)