package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.data.headlinesmodel.ErrorResponce
import com.aston.astonintensivfinal.data.headlinesmodel.NewApiResponce
import com.aston.astonintensivfinal.data.retrofit.NewsApiHeadlinesInterface
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsListArticles
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelError
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsError
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class OneSourceRepositoryImpl @Inject constructor(private val newsApiHeadlinesInterface: NewsApiHeadlinesInterface) :
    OneSourcesRepository {
    override suspend fun fetchOneSourceNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        source: String,
        search: String
    ): ApiResponse {
        return newsApiHeadlinesInterface.getNewsFromOneSource(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            sources = source,
            search = search
        )
    }

    override suspend fun saveNewsInDataBase(listNewsModelEntity: List<NewsModelEntity>) {
       AstonIntensivApplication.getAstonApplicationContext.newsDao.saveListNews(listNewsModelEntity)
    }

    override suspend fun loadNewsFromDataBase(sourceId: String): List<NewsModelEntity> {
       return AstonIntensivApplication.getAstonApplicationContext.newsDao.findNewsBySourceName(sourceId = sourceId)
    }

    override suspend fun mapOneSourceNewsInDomain(apiResponse: ApiResponse): OneSourceNewsDomain {
        when (apiResponse) {

            is NewApiResponce -> {

                var listOneSourceNewsArticle: MutableList<OneSourceNewsArticle> = mutableListOf()
                apiResponse.articles?.map {
                    listOneSourceNewsArticle.add(
                        OneSourceNewsArticle(
                            source = it.source?.name ?: "",
                            author = it.author ?: "",
                            title = it.title ?: "",
                            description = it.description ?: "",
                            url = it.url ?: "",
                            urlToImage = it.urlToImage ?: "",
                            publishedAt = it.publishedAt ?: "",
                            content = it.content ?: "",
                            idSource = it.source?.id ?: ""
                        )
                    )

                }
                val oneSourceListArticles: OneSourceNewsListArticles = OneSourceNewsListArticles(
                    totalResults = apiResponse.totalResults ?: 0,
                    articles = listOneSourceNewsArticle
                )
                return oneSourceListArticles

            }

            is ErrorResponce -> {


                val onSourceNewsError = OneSourceNewsError(
                    status = apiResponse.status ?: "error",
                    code = apiResponse.code ?: "error",
                    message = apiResponse.message ?: "error when load news from one source"
                )
                return onSourceNewsError

            }
        }


    }

    override suspend fun mapFromOneSourceToDomain(listNewsModelEntity: List<NewsModelEntity>): List<OneSourceNewsArticle> {
        fun toTimestamp(date: Date): String {
            return date.let { SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US).format(it) }
        }
        return listNewsModelEntity.map { OneSourceNewsArticle(source = it.sourceName, author = "", title = it.title, description = it.description, url = it.url, urlToImage = it.urlToImage, publishedAt = toTimestamp(it.publishedAt), content = it.content, idSource = it.sourceId) }
    }

    override suspend fun mapInNewsModelFromDomain(listOneSourceNewsArticle: List<OneSourceNewsArticle>): List<NewsModelEntity> {
        fun fromTimestamp(value: String): Date {
            return value.let { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).parse(it) } as Date
        }
        return listOneSourceNewsArticle.map { NewsModelEntity(urlToImage = it.urlToImage, title = it.title, content = it.content, publishedAt = fromTimestamp(it.publishedAt), sourceName = it.source, description = it.description, url = it.url, sourceId = it.idSource)  }
    }
}