package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.BigObject
import com.example.bookshelfapp.model.Books
import com.example.bookshelfapp.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getBookshelf(): String
}

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBookshelf(): String = bookshelfApiService.getBookshelf("jazz+history")
}