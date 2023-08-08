package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class UserNicknameDomainModel(

    @SerializedName("nickname")
    val nickname: String

)