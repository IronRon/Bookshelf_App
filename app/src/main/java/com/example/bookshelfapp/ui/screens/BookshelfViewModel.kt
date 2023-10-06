package com.example.bookshelfapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.BookshelfRepository
import com.example.bookshelfapp.model.BigObject
import com.example.bookshelfapp.model.Books
import com.example.bookshelfapp.model.Thumbnails

import com.example.bookshelfapp.model.Volume1
import com.google.gson.Gson

import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import java.io.IOException
import java.util.Objects

sealed interface BookshelfUiState {
    data class Success(val books: MutableList<String?>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getBookshelf()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getBookshelf() {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                val jsonstr = bookshelfRepository.getBookshelf()
                val json = Json.parseToJsonElement(jsonstr)
                val urlsList = json.jsonObject["items"]?.jsonArray
                val listOfUrls = mutableListOf<String?>()
                if (!urlsList.isNullOrEmpty()) {
                    // Iterate through the list
                    for (urlElement in urlsList) {
                        val url = urlElement.jsonObject["volumeInfo"]?.jsonObject?.get("imageLinks")?.jsonObject?.get("thumbnail")?.jsonPrimitive?.content
                        listOfUrls.add(url)
                    }
                }
                BookshelfUiState.Success(listOfUrls)
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                Log.d("plsHelp", "$e")
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}