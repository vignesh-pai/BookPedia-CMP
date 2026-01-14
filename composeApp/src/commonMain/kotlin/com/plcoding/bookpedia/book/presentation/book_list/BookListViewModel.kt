package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.domain.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val repository: BookRepository,
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var favoriteBooksJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observerFavoriteBooks()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _state.value,
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }

            is BookListAction.OnBookClick -> {
                // Handle book click action
            }

            is BookListAction.OnTabSelected -> {
                _state.update { it.copy(selectedTabIndex = action.index) }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                searchResults = cachedBooks,
                                isLoading = false,
                                errorMessage = null,
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun observerFavoriteBooks() {
        favoriteBooksJob?.cancel()
        favoriteBooksJob = viewModelScope.launch {
            repository.getFavouriteBooks()
                .onEach { favoriteBooks ->
                    _state.update { it.copy(favoriteBooks = favoriteBooks) }
                }.launchIn(viewModelScope)
        }
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        repository.searchBooks(query).onSuccess { searchResults ->
            _state.update {
                it.copy(
                    searchResults = searchResults,
                    isLoading = false,
                    errorMessage = null,
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    isLoading = false,
                    searchResults = emptyList(),
                    errorMessage = error.toUiText(),
                )
            }
        }
    }
}
