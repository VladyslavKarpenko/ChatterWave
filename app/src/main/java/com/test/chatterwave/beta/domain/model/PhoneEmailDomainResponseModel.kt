package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class PhoneEmailDomainResponseModel(
    @SerializedName("exists")
    val exists: Boolean
)

