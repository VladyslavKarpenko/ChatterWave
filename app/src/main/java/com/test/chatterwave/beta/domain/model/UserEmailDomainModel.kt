package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class UserEmailDomainModel(

    @SerializedName("email")
    val email: String

)