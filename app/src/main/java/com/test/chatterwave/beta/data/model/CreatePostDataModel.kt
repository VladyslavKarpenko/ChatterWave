package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class CreatePostDataModel(
    @SerializedName("description")
    val description: String?,
    @SerializedName("hashtagsNames")
    val hashtagsNames: List<String>?,
    @SerializedName("source")
    val createPostSourceDataModel: List<CreatePostSourceDataModel>?
)