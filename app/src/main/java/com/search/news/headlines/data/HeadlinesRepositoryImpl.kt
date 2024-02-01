package com.search.news.headlines.data

import com.search.news.SearchApplication
import com.search.news.core.data.databaseNews.NewsModel.NewsModelEntity
import com.search.news.core.data.headlinesmodel.ApiResponse
import com.search.news.core.data.headlinesmodel.ErrorResponce
import com.search.news.core.data.headlinesmodel.NewApiResponce
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsListArticles
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelError
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class HeadlinesRepositoryImpl @Inject constructor() : HeadlinesRepository {
    override fun fetchHeadlineNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        category: String,
        language: String,
        country: String,
        query: String
    ): Single<ApiResponse> {
        return SearchApplication.getAstonApplicationContext.headlinesApi.getGeneralHeadlines(
            apiKey,
            page,
            pageSize,
            category,
            language,
            country,
            query = query
        )
    }

    override fun fetchHeadlinesNewsFromEverything(
        apiKey: String,
        page: Int,
        pageSize: Int,
        dayFrom: String,
        dayTo: String,
        sortBy: String,
        query: String,
        language: String
    ): Single<ApiResponse> {
        return SearchApplication.getAstonApplicationContext.headlinesApi.getGeneralFromEverything(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            search = query,
            from = dayFrom,
            to = dayTo,
            sortBy = sortBy,
            language = language
        )
    }

    override suspend fun findNewsInDataBase(searchString: String): List<NewsModelEntity> {
        return SearchApplication.getAstonApplicationContext.newsDao.searchNewsByPartialString(
            searchString
        )
    }

    override suspend fun loadNewsFromDataBase(
        category: String,
        language: String
    ): List<NewsModelEntity> {
        return SearchApplication.getAstonApplicationContext.newsDao.getNewsByCategoryAndLanguageSortedByDate(
            category = category,
            language = language
        )
    }

    override suspend fun loadNewsByDateFromDataBase(
        category: String,
        language: String,
        startDate: Date,
        endDate: Date
    ): List<NewsModelEntity> {
        return SearchApplication.getAstonApplicationContext.newsDao.getNewsByCategoryAndLanguageAndDateSortedByDate(
            category = category,
            language = language,
            startDate = startDate,
            endDate = endDate
        )
    }

    override suspend fun saveNewsInDataBase(newsList: List<NewsModelEntity>) {
        SearchApplication.getAstonApplicationContext.newsDao.saveListNews(newsList)
    }


    override fun mapHeadlinesNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        category: String,
        language: String,
        country: String,
        query: String
    ): Single<HeadlineNewsResponce> {
        return fetchHeadlineNews(
            apiKey,
            page,
            pageSize,
            category,
            language,
            country,
            query
        ).subscribeOn(Schedulers.io()).map { responce ->
            when (responce) {
                is NewApiResponce -> {
                    val newsApiResponce = responce as NewApiResponce
                    var headlinesListModelData: MutableList<HeadlinesNewsModelData> =
                        mutableListOf()
                    newsApiResponce.articles?.map {
                        headlinesListModelData.add(
                            HeadlinesNewsModelData(
                                sourceid = it.source?.id ?: "",
                                sourceName = it.source?.name ?: "",
                                author = it.author,
                                title = it.title,
                                description = it.description,
                                url = it.url,
                                urlToImage = it.urlToImage,
                                publishedAt = it.publishedAt,
                                content = it.content
                            )
                        )

                    }
                    val headlinesNewsListArticles = HeadlinesNewsListArticles(
                        totalResults = newsApiResponce.totalResults,
                        articles = headlinesListModelData
                    )
                    headlinesNewsListArticles

                }

                is ErrorResponce -> {

                    val errorResponce = responce as ErrorResponce
                    val headlinesNewsModelError = HeadlinesNewsModelError(
                        errorResponce.status,
                        errorResponce.code,
                        errorResponce.message
                    )
                    headlinesNewsModelError

                }
            }
        }


    }

    override fun mapFromDBNewsInDomain(newsList: List<NewsModelEntity>): List<HeadlinesNewsModelData> {
        fun toTimestamp(date: Date): String {
            return date.let { SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US).format(it) }
        }
        return newsList.map {
            HeadlinesNewsModelData(
                sourceid = it.sourceId,
                sourceName = it.sourceName,
                author = "",
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = toTimestamp(it.publishedAt),
                content = it.content
            )
        }
    }

    override fun mapFromDomainNewsInDB(newsList: List<HeadlinesNewsModelData>): List<NewsModelEntity> {
        fun fromTimestamp(value: String): Date {
            return value.let {
                SimpleDateFormat(
                    //  "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    "MMM dd, yyyy | hh:mm a",
                    Locale.US
                ).parse(it)
            } as Date
        }
        return newsList.map {
            NewsModelEntity(
                urlToImage = it.urlToImage ?: "",
                title = it.title ?: "",
                content = it.content ?: "",
                publishedAt = fromTimestamp(it.publishedAt as String),
                sourceName = it.sourceName,
                sourceId = it.sourceid,
                description = it.description ?: "",
                url = it.url ?: "",

                )
        }
    }

    override fun mapListHeadlinesNews(apiResponce: Single<ApiResponse>): Single<HeadlineNewsResponce> {
        return apiResponce.subscribeOn(Schedulers.io()).map { responce ->
            when (responce) {
                is NewApiResponce -> {
                    val newsApiResponce = responce as NewApiResponce
                    var headlinesListModelData: MutableList<HeadlinesNewsModelData> =
                        mutableListOf()
                    newsApiResponce.articles?.map {
                        headlinesListModelData.add(
                            HeadlinesNewsModelData(
                                sourceid = it.source?.id ?: "",
                                sourceName = it.source?.name ?: "",
                                author = it.author,
                                title = it.title,
                                description = it.description,
                                url = it.url,
                                urlToImage = it.urlToImage,
                                publishedAt = it.publishedAt,
                                content = it.content
                            )
                        )

                    }
                    val headlinesNewsListArticles = HeadlinesNewsListArticles(
                        totalResults = newsApiResponce.totalResults,
                        articles = headlinesListModelData
                    )
                    headlinesNewsListArticles

                }

                is ErrorResponce -> {

                    val errorResponce = responce as ErrorResponce
                    val headlinesNewsModelError = HeadlinesNewsModelError(
                        errorResponce.status,
                        errorResponce.code,
                        errorResponce.message
                    )
                    headlinesNewsModelError

                }
            }
        }


    }
}