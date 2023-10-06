package com.example.bookshelfapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnails(
    @SerialName("volumeInfo")
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    @SerialName("imageLinks")
    val imageLinks: ImageLinks
)

@Serializable
data class ImageLinks(
    @SerialName("thumbnail")
    val thumbnail: String
)

@Serializable
data class VolumeInfo1(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    @SerialName("imageLinks") val imageLinks: ImageLinks1
)

@Serializable
data class ImageLinks1(
    val thumbnail: String,
    val smallThumbnail: String
)

@Serializable
data class Volume1(
    val volumeInfo: VolumeInfo1
)