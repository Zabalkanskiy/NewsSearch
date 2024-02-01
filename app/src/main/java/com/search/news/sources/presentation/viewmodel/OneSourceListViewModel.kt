package com.search.news.sources.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.news.common.mviState.FilterState
import com.search.news.common.mviState.Language
import com.search.news.common.mviState.Sort
import com.search.news.core.data.retrofit.NEWSAPIKEY
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsError
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import com.search.news.sources.domain.oneSourcesUesCase.FindNewsFromDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.GetNewsSourceUseCase
import com.search.news.sources.domain.oneSourcesUesCase.LoadNewsByDateFromDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.LoadNewsSourceFromDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.SaveNewsSourceInDBUseCase
import com.search.news.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsArticleVM
import com.search.news.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsErrorVM
import com.search.news.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsLIstArticlesVM
import com.search.news.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsVM
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class OneSourceListViewModel @Inject constructor(
    val getNewsSourceUseCase: GetNewsSourceUseCase,
    val loadNewsSourceFromDBUseCase: LoadNewsSourceFromDBUseCase,
    val saveNewsSourceInDBUseCase: SaveNewsSourceInDBUseCase,
    val loadNewsByDateFromDBUseCase: LoadNewsByDateFromDBUseCase,
    val findNewsFromDBUseCase: FindNewsFromDBUseCase
) :
    ViewModel() {
    private val _mutableNews: MutableLiveData<OneSourceNewsLIstArticlesVM> = MutableLiveData()
    private val _mutableNetworkError: MutableLiveData<OneSourceNewsErrorVM> = MutableLiveData()
    private val _mutableInternalError: MutableLiveData<OneSourceNewsErrorVM> = MutableLiveData()
    val getNetworkNewsError: LiveData<OneSourceNewsErrorVM> = _mutableNetworkError
    val getInterlalNewsError: LiveData<OneSourceNewsErrorVM> = _mutableInternalError

    var pageCount: Int = 1


    val getListNews = MediatorLiveData<OneSourceNewsLIstArticlesVM>().apply {
        addSource(_mutableNews) { value ->
            if (value.totalResults != 0 && value.articles.size != 0) {
                this.value = value
            }
        }
    }


    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is IOException) {
            _mutableNetworkError.postValue(
                OneSourceNewsErrorVM(
                    status = "error",
                    "Network error",
                    message = "IO Network error catch CoroutineExceptionHandler"
                )
            )
        } else {
            _mutableInternalError.postValue(
                OneSourceNewsErrorVM(
                    status = "error",
                    code = "Internal error",
                    message = "Internal error catch CoroutineExceptionHandler"
                )
            )

        }
    }

    val coroutineDBExeptionHandler = CoroutineExceptionHandler { _, _ ->
        _mutableInternalError.postValue(
            OneSourceNewsErrorVM(
                status = "error",
                code = "Internal error",
                message = "Internal error catch CoroutineExceptionHandler"
            )
        )
    }

    private fun loadNewsFromOneSources(
        apiKey: String,
        page: Int,
        pageSize: Int,
        source: String,
        search: String,
        language: String,
        sortBy: String,
        fromDate: String,
        toDate: String
    ) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            val oneSourceNewsVM: OneSourceNewsVM = getNewsSourceUseCase.getOneSourceNews(
                apiKey,
                page,
                pageSize,
                source,
                search = search,
                language = language,
                sortBy = sortBy,
                fromDate = fromDate,
                toDate = toDate
            ).let {
                mapDomainToOneSourceNewVM(it)
            }

            when (oneSourceNewsVM) {
                is OneSourceNewsLIstArticlesVM -> {
                    val currentItems = _mutableNews.value ?: OneSourceNewsLIstArticlesVM(
                        totalResults = 0,
                        articles = emptyList()
                    )
                    val updateItem: List<OneSourceNewsArticleVM> =
                        ArrayList(currentItems.articles + oneSourceNewsVM.articles)
                    _mutableNews.postValue(
                        OneSourceNewsLIstArticlesVM(
                            totalResults = oneSourceNewsVM.totalResults,
                            articles = updateItem
                        )
                    )

                    mapNewsVMtoNewsDomain(oneSourceNewsVM.articles).let {
                        saveNewsSourceInDBUseCase.saveNewsInDB(it, language = language)
                    }


                }

                is OneSourceNewsErrorVM -> {

                    _mutableInternalError.postValue(oneSourceNewsVM)


                }
            }

        }

    }

    private fun mapDomainToOneSourceNewVM(oneSourceNewsDomain: OneSourceNewsDomain): OneSourceNewsVM {

        when (oneSourceNewsDomain) {

            is OneSourceNewsListArticles -> {

                var listOneSourceNewsArticleVM: MutableList<OneSourceNewsArticleVM> =
                    mutableListOf()
                oneSourceNewsDomain.articles.map {
                    listOneSourceNewsArticleVM.add(
                        OneSourceNewsArticleVM(
                            source = it.source,
                            author = it.author,
                            title = it.title,
                            description = it.description,
                            url = it.url,
                            urlToImage = it.urlToImage,

                            publishedAt = convertDateFormat(it.publishedAt),

                            content = it.content,
                            idSource = it.idSource
                        )
                    )

                }
                val oneSourceListArticlesVM: OneSourceNewsLIstArticlesVM =
                    OneSourceNewsLIstArticlesVM(
                        totalResults = oneSourceNewsDomain.totalResults,
                        articles = listOneSourceNewsArticleVM
                    )
                return oneSourceListArticlesVM

            }

            is OneSourceNewsError -> {


                val onSourceNewsErrorVM = OneSourceNewsErrorVM(
                    status = oneSourceNewsDomain.status,
                    code = oneSourceNewsDomain.code,
                    message = oneSourceNewsDomain.message
                )
                return onSourceNewsErrorVM

            }
        }


    }

    fun loadNextData(
        source: String,
        searchString: String = "",
        isNetwork: Boolean,
        language: Language,
        fromDate: String,
        toDate: String,
        sortBy: Sort
    ) {
        val lang = convertLanguage(language)
        val sort = converSortToString(sortBy)
        var startDate: Date? = null
        var endDate: Date? = null
        if (fromDate.isNotBlank()) {
             startDate = stringToDate(fromDate)
             endDate = stringToDate(toDate)
        }
        if (isNetwork) {
            loadNewsFromOneSources(
                apiKey = NEWSAPIKEY,
                page = pageCount,
                pageSize = 20,
                source = source,
                search = searchString,
                language = lang,
                sortBy = sort,
                fromDate = fromDate,
                toDate = toDate
            )
        } else if (searchString.isBlank() && fromDate.isBlank()) {
            viewModelScope.launch(Dispatchers.IO + coroutineDBExeptionHandler) {
                val list: OneSourceNewsLIstArticlesVM =
                    loadNewsSourceFromDBUseCase.loadNewsFromDB(sourceId = source, language = lang)
                        .let {
                            mapOneSourceListArticlesToVM(oneSourceNewsListArticles = it)
                        }
                _mutableNews.postValue(list)

            }
        } else if (searchString.isBlank() && fromDate.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO + coroutineDBExeptionHandler) {
                val list: OneSourceNewsLIstArticlesVM =
                    loadNewsByDateFromDBUseCase.loadNewsByDateFromDB(
                        sourceId = source,
                        language = lang,
                        startDate = startDate as Date,
                        endDate = endDate as Date
                    ).let {
                        mapOneSourceListArticlesToVM(oneSourceNewsListArticles = it)
                    }
                _mutableNews.postValue(list)
            }


        } else {
            viewModelScope.launch(Dispatchers.IO + coroutineDBExeptionHandler) {
                val list: OneSourceNewsLIstArticlesVM = findNewsFromDBUseCase.findNewsFromDataBase(
                    sourceId = source,
                    language = lang,
                    query = searchString
                ).let {
                    mapOneSourceListArticlesToVM(oneSourceNewsListArticles = it)
                }
                _mutableNews.postValue(list)
            }
        }
        pageCount += 1
    }


    fun clearData() {
        pageCount = 1
        _mutableNews.value = OneSourceNewsLIstArticlesVM(totalResults = 0, articles = emptyList())

    }

    fun searchOnString(
        source: String,
        searchString: String,
        isNetwork: Boolean,
        language: Language,
        fromDate: String,
        toDate: String,
        sortBy: Sort
    ) {
        clearData()
        loadNextData(
            source = source,
            searchString = searchString,
            isNetwork = isNetwork,
            language = language,
            fromDate = fromDate,
            toDate = toDate,
            sortBy = sortBy
        )
    }

    fun convertDateFormat(input: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US)
        val date = inputFormat.parse(input)
        return outputFormat.format(date)
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

    fun clearMediator() {
        getListNews.postValue(OneSourceNewsLIstArticlesVM(totalResults = 0, articles = emptyList()))
    }

    fun stringToDate(dateString: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.parse(dateString)!!
    }

    fun mapNewsVMtoNewsDomain(listNews: List<OneSourceNewsArticleVM>): List<OneSourceNewsArticle> {
        return listNews.map {
            OneSourceNewsArticle(
                source = it.source,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                idSource = it.idSource
            )
        }
    }

    fun mapOneSourceListArticlesToVM(oneSourceNewsListArticles: OneSourceNewsListArticles): OneSourceNewsLIstArticlesVM {
        return OneSourceNewsLIstArticlesVM(
            totalResults = oneSourceNewsListArticles.totalResults,
            articles = mapListArticlesToVM(oneSourceNewsListArticles.articles)
        )
    }

    fun mapListArticlesToVM(listNews: List<OneSourceNewsArticle>): List<OneSourceNewsArticleVM> {
        return listNews.map {
            OneSourceNewsArticleVM(
                source = it.source,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                idSource = it.idSource
            )
        }

    }
}