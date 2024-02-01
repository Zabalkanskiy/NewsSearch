package com.search.news.headlines.domain.fullNewsUseCases

import com.search.news.headlines.data.HeadlinesFullNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindNewsInDatabaseUseCase @Inject constructor(private val headlinesFullNewsRepository: HeadlinesFullNewsRepository) {
    suspend operator fun invoke(title: String, urlToImage: String): Boolean = withContext(
        Dispatchers.IO
    ) {
        headlinesFullNewsRepository.findNewsInDataBase(title, urlToImage)
    }
}