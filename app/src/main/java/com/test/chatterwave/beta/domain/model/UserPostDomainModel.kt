package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class UserPostDomainModel(
    @SerializedName("_count")
    val likesCount: PostLikesCountDomainModel,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("source")
    val source: List<String>,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String
)