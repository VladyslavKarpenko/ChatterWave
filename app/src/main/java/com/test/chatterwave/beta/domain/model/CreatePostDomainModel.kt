package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class CreatePostDomainModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("hashtagsNames")
    val hashtagsNames: List<String>,
    @SerializedName("source")
    val createPostSourceDataModel: List<CreatePostSourceDomainModel>
)