package com.stevdza_san.demo.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevdza_san.demo.data.BookDatabase
import com.stevdza_san.demo.domain.Book
import com.stevdza_san.demo.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: BookDatabase
) : ViewModel() {
    private var _sortedByFavorite = MutableStateFlow(false)
    val sortedByFavorite: StateFlow<Boolean> = _sortedByFavorite

    private var _books: MutableState<RequestState<List<Book>>> =
        mutableStateOf(RequestState.Loading)
    val books: State<RequestState<List<Book>>> = _books

    init {
        viewModelScope.launch {
            _sortedByFavorite.collectLatest { favorite ->
                if (favorite) {
                    database.bookDao()
                        .readAllBooksSortByFavorite()
                        .collectLatest { sortedBooks ->
                            _books.value = RequestState.Success(
                                data = sortedBooks.sortedBy { !it.isFavorite }
                            )
                        }
                } else {
                    database.bookDao()
                        .readAllBooks()
                        .collectLatest { allBooks ->
                            _books.value = RequestState.Success(
                                data = allBooks.sortedBy { it.isFavorite }
                            )
                        }
                }
            }
        }
    }

    fun toggleSortByFavorite() {
        _sortedByFavorite.value = !_sortedByFavorite.value
    }

}