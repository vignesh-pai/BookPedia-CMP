package com.plcoding.bookpedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreen
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListState
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
private fun BookSearchBarPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxWidth().background(Color.White)
        ) {
            BookSearchBar(
                searchQuery = "Sample Query",
                onSearchQueryChange = {},
                onImeSearch = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private val books = List(10) {
    Book(
        id = it.toString(),
        imageUrl = "https://covers.openlibrary.org/b/id/8231856-L.jpg",
        firstPublishYear = null,
        title = "Book $it",
        authors = listOf("Author One", "Author Two"),
        description = null,
        languages = emptyList(),
        averageRating = 4.6789,
        ratingsCount = 3,
        numPages = 100,
        numEditions = 100,
    )
}

@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = books,
        ),
        onAction = {}
    )
}
