package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.database.BookEntity
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book

fun SearchedBookDto.toBook() = Book(
    id = id.substringAfterLast("/"),
    title = title,
    imageUrl = if (coverKey != null) {
        "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
    } else {
        "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
    },
    authors = authorNames ?: emptyList(),
    description = null,
    languages = languages ?: emptyList(),
    firstPublishYear = firstPublishYear.toString(),
    averageRating = ratingAverage,
    ratingsCount = ratingsCount,
    numPages = numberOfPagesMedian,
    numEditions = numEditions,
)

fun Book.toBookEntity() = BookEntity(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    languages = languages,
    authors = authors,
    firstPublishYear = firstPublishYear,
    ratingsAverage = averageRating,
    ratingsCount = ratingsCount,
    numPages = numPages,
    numEditions = numEditions,
)

fun BookEntity.toBook() = Book(
    id = id,
    title = title,
    imageUrl = imageUrl,
    authors = authors,
    description = description,
    languages = languages,
    firstPublishYear = firstPublishYear,
    averageRating = ratingsAverage,
    ratingsCount = ratingsCount,
    numPages = numPages,
    numEditions = numEditions,
)
