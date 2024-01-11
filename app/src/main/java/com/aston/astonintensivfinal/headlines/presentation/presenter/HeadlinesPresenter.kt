package com.aston.astonintensivfinal.headlines.presentation.presenter

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.headlinesmodel.ErrorResponce
import com.aston.astonintensivfinal.data.headlinesmodel.NewApiResponce
import com.aston.astonintensivfinal.headlines.domain.GetHeadLinesUseCase
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsListArticles
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsModelError
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.HeadlinesView
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model.HPresenterNewsListArticles
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model.HPresenterNewsModelData
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model.HPresenterNewsModelError
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model.HeadlinesPresenterNewsResponce
import com.aston.astonintensivfinal.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject


class HeadlinesPresenter @Inject constructor(val headlinesUseCase: GetHeadLinesUseCase) :
    MvpPresenter<HeadlinesView>() {
    var category: Category = Category.GENERAL
    var pageCount: Int = 1
    var hPresenterListNewsModelData: MutableList<HPresenterNewsModelData> = mutableListOf()
    fun loadData() {
        // Здесь можно получить данные, например, из репозитория или сетевого источника.
        // А затем использовать getViewState() для взаимодействия с View.
        // Пример:
        try {
            AstonIntensivApplication.getAstonApplicationContext.headlinesApi.getGeneralHeadlines(
                apiKey = Utils.NEWSAPIKEY,
                page = 1
            )
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    when (apiResponse) {
                        is NewApiResponce -> {
                            // Обработка результатов запроса
                            val newsApiResponce = apiResponse as NewApiResponce

                            //   viewState.showData(newsApiResponce.articles as List<Article>)
                        }

                        is ErrorResponce -> {
                            // Обработка ошибки в запросе
                        }
                    }


                }, { trowable ->
                    // Обработка сетевой ошибки
                })
            val data = "Fetched Data"

            //  viewState.showData(data)

        } catch (e: Exception) {
            viewState.showError(e.message ?: "Unknown error")
        }
    }

    fun mapNewsResponce(
        apiKey: String,
        page: Int,
        pageSize: Int,
        category: String,
        language: String,
        country: String
    ): Single<HeadlinesPresenterNewsResponce> {
        return headlinesUseCase.getHeadlines(apiKey, page, pageSize, category, language, country)
            .subscribeOn(Schedulers.io()).map { response ->
                when (response) {
                    is HeadlinesNewsListArticles -> {
                        var hPresenterNewsModelListData: MutableList<HPresenterNewsModelData> =
                            mutableListOf()
                        response.articles.map {
                            hPresenterNewsModelListData.add(
                                HPresenterNewsModelData(
                                    it.source,
                                    it.title,
                                    it.description,
                                    it.url,
                                    it.urlToImage,
                                    it.publishedAt,
                                    it.content
                                )
                            )
                        }
                        val hPresenterNewsListArticles: HPresenterNewsListArticles =
                            HPresenterNewsListArticles(
                                totalResults = response.totalResults,
                                articles = hPresenterNewsModelListData
                            )
                        hPresenterNewsListArticles

                    }

                    is HeadlinesNewsModelError -> {
                        val hpresenterNewsModelError: HPresenterNewsModelError =
                            HPresenterNewsModelError(
                                response.status,
                                response.code,
                                response.message
                            )
                        hpresenterNewsModelError

                    }
                }
            }
    }

    fun loadNextData() {

        try {
            var categoryString = "general"
            when (category) {
                Category.GENERAL -> {
                    categoryString = "general"
                }

                Category.BUSINESS -> {
                    categoryString = "business"
                }

                Category.TRAVELLING -> {
                    categoryString = "general"
                }
            }


            mapNewsResponce(
                apiKey = Utils.NEWSAPIKEY,
                page = pageCount,
                pageSize = 20,
                category = categoryString,
                language = "en",
                country = "us"
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    when (apiResponse) {
                        is HPresenterNewsListArticles -> {
                            hPresenterListNewsModelData.addAll(apiResponse.articles)
                            val newList = hPresenterListNewsModelData.toList()
                            viewState.showData(totalResults = apiResponse.totalResults, newList)
                            pageCount += 1

                        }

                        is HPresenterNewsModelError -> {
                            //error from server
                        }
                    }

                }, { trowable ->

                }
                )
        } catch (e: Exception) {

            viewState.showError(e.message ?: "Unknown error")

        }


    }

    fun clearData() {
        pageCount = 1
        hPresenterListNewsModelData.clear()

    }

    fun changeCategory(newCategory: Category) {
        if (category == newCategory) {

        } else {
            when (newCategory) {
                Category.GENERAL -> {
                    category = Category.GENERAL
                    viewState.changeTab()
                    clearData()
                    loadNextData()


                }

                Category.BUSINESS -> {
                    category = Category.BUSINESS
                    viewState.changeTab()
                    clearData()
                    loadNextData()

                }

                Category.TRAVELLING -> {
                    category = Category.TRAVELLING
                    viewState.changeTab()
                    clearData()
                    loadNextData()

                }

            }
        }
    }

    override

    fun onFirstViewAttach() {
        super.onFirstViewAttach()
        // loadData()
    }


}
