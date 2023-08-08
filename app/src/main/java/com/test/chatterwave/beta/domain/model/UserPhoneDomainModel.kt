package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class UserPhoneDomainModel(

    @SerializedName("phoneNumber")
    val phoneNumber: String

    )
