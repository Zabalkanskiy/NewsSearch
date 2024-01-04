package com.aston.astonintensivfinal.headlines.data

import com.aston.astonintensivfinal.data.ApiResponse
import com.aston.astonintensivfinal.headlines.domain.model.HeadlineNewsResponce
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface HeadlinesRepository {
  fun fetchHeadlineNews(apiKey: String, page: Int, pageSize: Int = 20, category: String = "general", language: String = "en",  country: String = "us"): Single<ApiResponse>
    fun mapHeadlinesNews(apiKey: String, page: Int, pageSize: Int, category: String, language: String,  country: String): Single<HeadlineNewsResponce>
}