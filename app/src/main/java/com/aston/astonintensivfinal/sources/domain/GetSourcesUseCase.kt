package com.aston.astonintensivfinal.sources.domain

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepositoryImpl
import com.aston.astonintensivfinal.sources.domain.model.SourceResponseDomain
import javax.inject.Inject

class GetSourcesUseCase @Inject  constructor(private val sourcesRepository: SourcesRepositoryImpl) {
  suspend  fun getSources(
        apiKey: String,
        page: Int,
        pageSize: Int
    ): SourceResponseDomain {
        return sourcesRepository.mapSourcesNews(
            apiKey,
            page,
            pageSize
        )
    }
}