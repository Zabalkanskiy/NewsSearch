package com.search.news.headlines.domain.fullNewsUseCases

import com.search.news.headlines.data.HeadlinesFullNewsRepository
import com.search.news.headlines.domain.model.FullNewsDomain.FullNewsDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteFullNewsUseCase @Inject constructor(private val headlinesFullNewsRepository: HeadlinesFullNewsRepository) {
    suspend operator fun invoke(news: FullNewsDomainModel) = withContext(Dispatchers.IO) {
        headlinesFullNewsRepository.mapToNewsModelEntity(news).let {
            headlinesFullNewsRepository.deleteFullNews(it)
        }
    }
}