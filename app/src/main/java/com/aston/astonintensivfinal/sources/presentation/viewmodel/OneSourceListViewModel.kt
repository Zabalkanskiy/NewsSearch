package com.aston.astonintensivfinal.sources.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsError
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase.GetNewsSourceUseCase
import com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase.LoadNewsSourceFromDBUseCase
import com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase.SaveNewsSourceInDBUseCase
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsArticleVM
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsErrorVM
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsLIstArticlesVM
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.oneSourseList.OneSourceNewsVM
import com.aston.astonintensivfinal.utils.Utils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class OneSourceListViewModel @Inject constructor(val getNewsSourceUseCase: GetNewsSourceUseCase, val loadNewsSourceFromDBUseCase: LoadNewsSourceFromDBUseCase, val saveNewsSourceInDBUseCase: SaveNewsSourceInDBUseCase) :
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

    val coroutineDBExeptionHandler = CoroutineExceptionHandler {_, _ ->
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
        search: String
    ) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            val oneSourceNewsVM: OneSourceNewsVM = getNewsSourceUseCase.getOneSourceNews(
                apiKey,
                page,
                pageSize,
                source,
                search = search
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
                        saveNewsSourceInDBUseCase.saveNewsInDB(it)
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
                            publishedAt = it.publishedAt,
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

    fun loadNextData(source: String, searchString: String = "", isNetwork: Boolean) {
        if (isNetwork) {
            loadNewsFromOneSources(
                apiKey = Utils.NEWSAPIKEY,
                page = pageCount,
                pageSize = 20,
                source = source,
                search = searchString
            )
        } else {
            viewModelScope.launch(Dispatchers.IO + coroutineDBExeptionHandler) {
                loadNewsSourceFromDBUseCase.loadNewsFromDB(sourceId = source)
            }
        }
        pageCount += 1
    }


    fun clearData() {
        pageCount = 1
        _mutableNews.value = OneSourceNewsLIstArticlesVM(totalResults = 0, articles = emptyList())

    }

    fun searchOnString(source: String, searchString: String, isNetwork: Boolean) {
        clearData()
        loadNextData(source = source, searchString = searchString, isNetwork = isNetwork)
    }

    fun mapNewsVMtoNewsDomain(listNews: List<OneSourceNewsArticleVM>): List<OneSourceNewsArticle> {
        return listNews.map { OneSourceNewsArticle(source = it.source, author = it.author, title = it.title, description = it.description, url = it.url, urlToImage = it.urlToImage, publishedAt = it.publishedAt, content = it.content, idSource = it.idSource) }
    }
}