package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class UpdateUserDataModel(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("photo")
    val photo: String?
)