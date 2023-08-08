package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class UserPostDataModel(
    @SerializedName("_count")
    val likesCount: PostLikesCountDataModel?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("liked")
    val liked: Boolean?,
    @SerializedName("source")
    val source: List<String>?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: String?
)