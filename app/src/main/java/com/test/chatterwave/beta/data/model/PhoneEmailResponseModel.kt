package com.test.chatterwave.beta.data.model

import com.google.gson.annotations.SerializedName

data class PhoneEmailResponseModel(
    @SerializedName("exists")
    val exists: Boolean?
)
