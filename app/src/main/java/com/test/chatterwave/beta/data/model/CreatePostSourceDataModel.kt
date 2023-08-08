package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class CreatePostSourceDataModel(
    @SerializedName("base64File")
    val base64File: String?,
    @SerializedName("ext")
    val ext: String?
)