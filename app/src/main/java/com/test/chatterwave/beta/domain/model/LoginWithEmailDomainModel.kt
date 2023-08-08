package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class LoginWithEmailDomainModel(

    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String

)
