package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.sources.domain.model.SourceResponseDomain

interface SourcesRepository {
 suspend  fun fetchSourcesNews(apiKey: String, page: Int, pageSize: Int ): SourceResponse

 suspend  fun mapSourcesNews(apiKey: String, page: Int, pageSize: Int): SourceResponseDomain
}