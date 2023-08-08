package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class PostLikesCountDataModel(
    @SerializedName("likes")
    val likes: Int?
)