package com.plcoding.bookpedia.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            startDestination = Route.BookGraph,
            navController = navController
        ) {
            composable<Route.BookGraph> {
                val viewModel = koinViewModel<BookListViewModel>()
                BookListScreenRoute(
                    viewModel = viewModel,
                    onBookClick = { book ->
                        navController.navigate(Route.BookDetails(book.id))
                    }
                )
            }
            composable<Route.BookDetails> { entry ->
                val bookId = entry.toRoute<Route.BookDetails>().id
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Book Details for ID: $bookId")
                }
            }
        }

    }
}
