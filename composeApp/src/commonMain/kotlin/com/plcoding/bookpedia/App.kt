package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel

@Composable
@Preview
fun App() {
    BookListScreenRoute(
        viewModel = remember { BookListViewModel() },
        onBookClick = { }
    )
}