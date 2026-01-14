package com.plcoding.bookpedia.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailAction
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoute
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.book.presentation.book_list.SelectedBookViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            startDestination = Route.BookGraph,
            navController = navController
        ) {
            navigation<Route.BookGraph>(
                startDestination = Route.BookList
            ) {
                composable<Route.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel = it.shareKoinViewModel<SelectedBookViewModel>(
                        navController = navController
                    )
                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectedBook(null)
                    }
                    BookListScreenRoute(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onSelectedBook(book)
                            navController.navigate(Route.BookDetails(book.id))
                        }
                    )
                }
                composable<Route.BookDetails> {
                    val selectedBookViewModel = it.shareKoinViewModel<SelectedBookViewModel>(
                        navController = navController
                    )
                    val viewModel = koinViewModel<BookDetailViewModel>()
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            // If there's no selected book, navigate back to the list
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                        }
                    }

                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.shareKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}
