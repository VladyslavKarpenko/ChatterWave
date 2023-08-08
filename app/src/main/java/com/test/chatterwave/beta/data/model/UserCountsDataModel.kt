package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class UserCountsDataModel(
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("posts")
    val posts: Int?
)