package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class UserCountsDomainModel(
    @SerializedName("followers")
    val followers: String,
    @SerializedName("following")
    val following: String,
    @SerializedName("posts")
    val posts: String

)