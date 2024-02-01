package com.search.news.sources.domain.sourceListUseCase

import com.search.news.sources.data.repository.SourcesRepositoryImpl
import com.search.news.sources.domain.model.sourceListModel.SourceResponseDomain
import javax.inject.Inject

class GetSourcesUseCase @Inject  constructor(private val sourcesRepository: SourcesRepositoryImpl) {
  suspend  fun getSources(
        apiKey: String,
        page: Int,
        pageSize: Int,
        language:String
    ): SourceResponseDomain {
        return sourcesRepository.mapSourcesNews(
            apiKey,
            page,
            pageSize,
            language = language
        )
    }
}