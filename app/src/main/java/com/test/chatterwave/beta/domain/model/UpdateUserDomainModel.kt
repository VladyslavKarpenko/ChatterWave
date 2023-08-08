package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class UpdateUserDomainModel(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("photo")
    var photo: String
)