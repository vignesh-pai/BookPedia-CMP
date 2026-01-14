package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListScreenRoute(
        viewModel = viewModel,
        onBookClick = { }
    )
}
