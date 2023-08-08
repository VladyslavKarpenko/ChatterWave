package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class ConfirmCodeErrorDataResponseModel(
    @SerializedName("error")
    val error: String?
)