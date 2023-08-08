package com.test.chatterwave.beta.domain.model

import com.google.gson.annotations.SerializedName

data class NicknameDomainResponseModel(
    @SerializedName("exists")
    val exists: Boolean

)