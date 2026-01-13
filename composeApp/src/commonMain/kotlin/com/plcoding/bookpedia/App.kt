package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    BookListScreenRoute(
        viewModel = remember { BookListViewModel() },
        onBookClick = { }
    )
}