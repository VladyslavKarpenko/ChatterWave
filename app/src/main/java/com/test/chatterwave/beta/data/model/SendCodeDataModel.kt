package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class SendCodeDataModel(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null
)