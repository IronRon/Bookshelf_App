package com.example.bookshelfapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BigObject(
    @SerialName("id")
    val kind: String,
    @SerialName("totalItems")
    val totalItems: Int,
    @SerialName("items")
    val items: List<Books>,
)

@Serializable
data class Books(
    @SerialName("id")
    val id: String,
    @SerialName("selfLink")
    val selfLink: String,
    @SerialName("volumeInfo")
    val volumeInfo: VolumeInfo
)