package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class PostLikesCountDomainModel(
    @SerializedName("likes")
    val likes: Int
)