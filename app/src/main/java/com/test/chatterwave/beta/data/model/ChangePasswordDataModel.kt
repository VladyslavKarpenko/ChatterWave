package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class ChangePasswordDataModel(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?
)