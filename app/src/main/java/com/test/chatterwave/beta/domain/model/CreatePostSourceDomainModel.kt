package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class CreatePostSourceDomainModel(
    @SerializedName("base64File")
    val base64File: String,
    @SerializedName("ext")
    val ext: String = "png"
)