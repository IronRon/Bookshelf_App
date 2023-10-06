package com.example.bookshelfapp.network

import com.example.bookshelfapp.model.BigObject
import com.example.bookshelfapp.model.Books
import com.example.bookshelfapp.model.Thumbnails
import retrofit2.http.GET
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBookshelf(@Query("q") jazz: String): String

    //@GET
    //suspend fun getThumbnail(@Query("?q") jazz: String): List<Thumbnails>
}