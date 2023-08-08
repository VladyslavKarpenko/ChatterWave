package com.test.chatterwave.beta.data.model

import com.google.gson.annotations.SerializedName

data class UserNicknameDataModel(

    @SerializedName("nickname")
    val nickname: String

)