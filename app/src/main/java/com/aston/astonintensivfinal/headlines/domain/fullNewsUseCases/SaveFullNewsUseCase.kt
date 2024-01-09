package com.aston.astonintensivfinal.headlines.domain.fullNewsUseCases

import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepository
import com.aston.astonintensivfinal.headlines.domain.model.FullNewsDomain.FullNewsDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveFullNewsUseCase constructor(private val headlinesFullNewsRepository: HeadlinesFullNewsRepository) {
    suspend operator fun invoke(news: FullNewsDomainModel) = withContext(Dispatchers.IO) {
        headlinesFullNewsRepository.mapToNewsModelEntity(news).let {
            headlinesFullNewsRepository.saveFullNews(it)
        }
    }


}