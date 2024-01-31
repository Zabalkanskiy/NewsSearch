package com.aston.astonintensivfinal.headlines.presentation.presenter

import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.common.mviState.Sort
import com.aston.astonintensivfinal.core.data.retrofit.NEWSAPIKEY
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.FindHeadlinesInDataBaseUse
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.GetHeadLinesUseCase
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.LoadHeadlinesByDateUseCase
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.LoadHeadlinesFromDataBaseUseCase
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.SaveHeadlinesDataInDataBaseUseCase
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsListArticles
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelError
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.HeadlinesView
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsListArticles
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelError
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HeadlinesPresenterNewsResponce
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class HeadlinesPresenter @Inject constructor(
    val headlinesUseCase: GetHeadLinesUseCase,
    val findHeadlinesInDataBaseUse: FindHeadlinesInDataBaseUse,
    val loadHeadlinesFromDataBaseUseCase: LoadHeadlinesFromDataBaseUseCase,
    val saveHeadlinesDataInDataBaseUseCase: SaveHeadlinesDataInDataBaseUseCase,
    val loadHeadlinesByDateUseCase: LoadHeadlinesByDateUseCase
) : MvpPresenter<HeadlinesView>() {

    var category: Category = Category.GENERAL
    var pageCount: Int = 1
    var hPresenterListNewsModelData: MutableList<HPresenterNewsModelData> = mutableListOf()
    var compositeDisposable = CompositeDisposable()


    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

        viewState.internalError()

    }

    val coroutineJob = Job()


    fun mapNewsResponce(data: Single<HeadlineNewsResponce>): Single<HeadlinesPresenterNewsResponce> {
        return data.subscribeOn(Schedulers.io()).map { response ->
            when (response) {
                is HeadlinesNewsListArticles -> {
                    var hPresenterNewsModelListData: MutableList<HPresenterNewsModelData> =
                        mutableListOf()
                    response.articles.map {
                        hPresenterNewsModelListData.add(
                            HPresenterNewsModelData(
                                source = it.sourceName,
                                idSource = it.sourceid,
                                it.title,
                                it.description,
                                it.url,
                                it.urlToImage,
                                convertDateFormat(it.publishedAt),
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

    fun fetchDataFromNetwork(
        apiKey: String,
        page: Int,
        pageSize: Int,
        dayFrom: String,
        dayTo: String,
        sortBy: String,
        query: String,
        language: String

    ): Single<HeadlinesPresenterNewsResponce> {
        return headlinesUseCase.getHeadlines(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            dayFrom = dayFrom,
            dayTo = dayTo,
            sortBy = sortBy,
            query = query,
            language = language,


            ).let {
            mapNewsResponce(it)
        }

    }

    fun loadNextData(
        isOnline: Boolean,
        query: String = "",
        filterState: FilterState
    ) {
        var searchString = query
        val getLanguage = convertLanguage(filterState.language)
        val getSort = converSortToString(filterState.sort)
        val fromDate = filterState.date?.start ?: ""
        val toDate = filterState.date?.end ?: ""


        var categoryString = "general"
        when (category) {
            Category.GENERAL -> {
                categoryString = "general"
            }

            Category.BUSINESS -> {
                categoryString = "business"
            }

            Category.TRAVELLING -> {
                categoryString = "travel"
            }
        }

        if (searchString.isBlank()) {
            searchString = categoryString
        }

        if (isOnline) {


            val disposable = fetchDataFromNetwork(
                apiKey = NEWSAPIKEY,
                page = pageCount,
                pageSize = 20,
                dayFrom = fromDate,
                dayTo = toDate,
                sortBy = getSort,
                query = searchString,
                language = getLanguage

            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    when (apiResponse) {
                        is HPresenterNewsListArticles -> {
                            hPresenterListNewsModelData.addAll(apiResponse.articles)
                            val newList = hPresenterListNewsModelData.toList()
                            viewState.showData(totalResults = apiResponse.totalResults, newList)
                            pageCount += 1


                            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler + coroutineJob).launch {

                                mapListHpresenterDataInDomain(newsList = apiResponse.articles).let {
                                    saveHeadlinesDataInDataBaseUseCase.saveDataInDataBase(
                                        it,
                                        category = categoryString,
                                        language = getLanguage
                                    )

                                }


                            }

                        }

                        is HPresenterNewsModelError -> {

                            viewState.internalError()
                        }
                    }


                }, { trowable ->
                    if (trowable is IOException) {
                        viewState.networkError()

                    } else {
                        viewState.internalError()
                    }

                }
                )
            compositeDisposable.add(disposable)
        } else {
            if (query.isBlank() && fromDate.isBlank()) {
                CoroutineScope(Dispatchers.IO + coroutineExceptionHandler + coroutineJob).launch {

                    val listData =
                        loadHeadlinesFromDataBaseUseCase.loadFromDataBase(category = categoryString, language = getLanguage).let {
                            mapListDomainDataInHpresenter(it)
                        }

                    withContext(Dispatchers.Main) {
                        viewState.showData(listData.size, data = listData)
                    }
                }
            } else if (fromDate.isNotBlank() && toDate.isNotBlank()) {
                val startDate: Date = stringToDate(dateString = fromDate)
                val endDate: Date = stringToDate(dateString = toDate)
                CoroutineScope(Dispatchers.IO + coroutineExceptionHandler + coroutineJob).launch {

                    val listData =
                        loadHeadlinesByDateUseCase.loadFromDataBaseByDate(category = categoryString, language = getLanguage, startDate = startDate, endDate = endDate).let {
                            mapListDomainDataInHpresenter(it)
                        }

                    withContext(Dispatchers.Main) {
                        viewState.showData(listData.size, data = listData)
                    }
                }

            } else{
                CoroutineScope(Dispatchers.IO + coroutineExceptionHandler + coroutineJob).launch {
                    val listData =
                        findHeadlinesInDataBaseUse.findHeadlinesInDataBase(searchQuery = query)
                            .let {
                                mapListDomainDataInHpresenter(it)
                            }

                    withContext(Dispatchers.Main) {
                        viewState.showData(listData.size, data = listData)
                    }
                }

            }


        }


    }

    fun clearData() {
        pageCount = 1
        hPresenterListNewsModelData.clear()

    }

    fun changeCategory(newCategory: Category, isOnline: Boolean, filterState: FilterState) {
        if (category == newCategory) {

        } else {
            when (newCategory) {
                Category.GENERAL -> {
                    category = Category.GENERAL
                    viewState.changeTab()
                    clearData()
                    loadNextData(isOnline = isOnline, filterState = filterState)


                }

                Category.BUSINESS -> {
                    category = Category.BUSINESS
                    viewState.changeTab()
                    clearData()
                    loadNextData(isOnline = isOnline, filterState = filterState)

                }

                Category.TRAVELLING -> {
                    category = Category.TRAVELLING
                    viewState.changeTab()
                    clearData()
                    loadNextData(isOnline = isOnline, filterState = filterState)

                }

            }
        }
    }


    fun convertDateFormat(input: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US)
        val date = inputFormat.parse(input)
        return outputFormat.format(date)
    }

    fun mapListHpresenterDataInDomain(newsList: List<HPresenterNewsModelData>): List<HeadlinesNewsModelData> {
        return newsList.map {
            HeadlinesNewsModelData(
                sourceid = it.idSource,
                sourceName = it.source ?: "",
                author = "",
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }
    }

    fun mapListDomainDataInHpresenter(newsList: List<HeadlinesNewsModelData>): List<HPresenterNewsModelData> {
        return newsList.map {
            HPresenterNewsModelData(
                source = it.sourceName,
                idSource = it.sourceid,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }

    }


    fun convertLanguage(language: Language): String {
        return when (language) {
            Language.English -> "en"
            Language.Russian -> "ru"
            Language.Deutsch -> "de"
        }
    }

    fun converSortToString(sort: Sort): String {
        return when (sort) {
            Sort.New -> "publishedAt"
            Sort.Popular -> "popularity"
            Sort.Relevant -> "relevancy"
        }
    }

    fun hasBage(filterState: FilterState): Boolean {
        if (filterState.language == Language.English && filterState.date == null && filterState.sort == Sort.New) {
            return false
        } else {
            return true
        }
    }
    fun stringToDate(dateString: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.parse(dateString)!!
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        coroutineJob.cancel()
        super.onDestroy()
    }


}
