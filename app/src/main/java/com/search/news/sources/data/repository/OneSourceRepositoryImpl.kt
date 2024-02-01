package com.search.news.sources.data.repository

import com.search.news.SearchApplication
import com.search.news.core.data.databaseNews.NewsModel.NewsModelEntity
import com.search.news.core.data.headlinesmodel.ApiResponse
import com.search.news.core.data.headlinesmodel.ErrorResponce
import com.search.news.core.data.headlinesmodel.NewApiResponce
import com.search.news.core.data.retrofit.NewsApiHeadlinesInterface
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsError
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
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
        search: String,
        language: String,
        sortBy: String,
        fromDate: String,
        toDate: String
    ): ApiResponse {

        return newsApiHeadlinesInterface.getNewsWithParametr(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            sources = source,
            search = search,
            from = fromDate,
            to = toDate,
            sortBy = sortBy,
            language = language
        )
    }

    override suspend fun saveNewsInDataBase(listNewsModelEntity: List<NewsModelEntity>) {
       SearchApplication.getAstonApplicationContext.newsDao.saveEverythingNews(listNewsModelEntity)
    }

    override suspend fun loadNewsFromDataBase(
        sourceId: String,
        language: String
    ): List<NewsModelEntity> {
       return SearchApplication.getAstonApplicationContext.newsDao.findNewsBySourceName(sourceId = sourceId, language = language)
    }

    override suspend fun loadNewsByDateFromDataBase(
        sourceId: String,
        language: String,
        startDate: Date,
        endDate: Date
    ): List<NewsModelEntity> {
        return SearchApplication.getAstonApplicationContext.newsDao.getNewsBySourceAndLanguageAndDateSortedByDate(
            sourceId = sourceId,
            language = language,
            startDate = startDate,
            endDate = endDate)
    }

    override suspend fun findNewsByQueryFromDataBase(
        sourceId: String,
        language: String,
        query: String
    ): List<NewsModelEntity> {
       return SearchApplication.getAstonApplicationContext.newsDao.searchNewsByQueryAndSources(sourceId = sourceId, language = language, query = query)
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

            return value.let{  SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US).parse(it) } as Date
        }
        return listOneSourceNewsArticle.map { NewsModelEntity(urlToImage = it.urlToImage, title = it.title, content = it.content, publishedAt = fromTimestamp(it.publishedAt), sourceName = it.source, description = it.description, url = it.url, sourceId = it.idSource)  }
    }
}