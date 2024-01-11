package com.aston.astonintensivfinal.headlines.data

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.data.headlinesmodel.ErrorResponce
import com.aston.astonintensivfinal.data.headlinesmodel.NewApiResponce
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsListArticles
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelError
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HeadlinesRepositoryImpl @Inject constructor(): HeadlinesRepository {
    override fun fetchHeadlineNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        category: String,
        language: String,
        country: String
    ): Single<ApiResponse> {
        return AstonIntensivApplication.getAstonApplicationContext.headlinesApi.getGeneralHeadlines(
            apiKey,
            page,
            pageSize,
            category,
            language,
            country
        )
    }

   override fun mapHeadlinesNews(

        apiKey: String,
        page: Int,
        pageSize: Int,
        category: String,
        language: String,
        country: String
    ): Single<HeadlineNewsResponce> {
       return fetchHeadlineNews(
            apiKey,
            page,
            pageSize,
            category,
            language,
            country
        ).subscribeOn(Schedulers.io()).map { responce ->
            when (responce) {
                is NewApiResponce -> {
                    val newsApiResponce = responce as NewApiResponce
                    var headlinesListModelData : MutableList<HeadlinesNewsModelData> = mutableListOf()
                    newsApiResponce.articles?.map{
                        headlinesListModelData.add(HeadlinesNewsModelData(it.source, it.author, it.title, it.description, it.url, it.urlToImage, it.publishedAt, it.content))

                    }
                    val headlinesNewsListArticles = HeadlinesNewsListArticles(totalResults = newsApiResponce.totalResults,articles = headlinesListModelData)
                    headlinesNewsListArticles

                }

                is ErrorResponce -> {

                    val errorResponce = responce as ErrorResponce
                    val headlinesNewsModelError = HeadlinesNewsModelError(errorResponce.status, errorResponce.code, errorResponce.message)
                    headlinesNewsModelError

                }
            }
        }


    }
}