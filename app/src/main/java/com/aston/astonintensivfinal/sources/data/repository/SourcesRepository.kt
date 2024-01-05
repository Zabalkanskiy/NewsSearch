package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.headlines.domain.model.HeadlineNewsResponce
import com.aston.astonintensivfinal.sources.domain.model.SourceResponseDomain
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface SourcesRepository {
 suspend  fun fetchSourcesNews(apiKey: String, page: Int, pageSize: Int ): SourceResponse

 suspend  fun mapSourcesNews(apiKey: String, page: Int, pageSize: Int): SourceResponseDomain
}