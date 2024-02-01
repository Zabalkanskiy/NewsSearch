package com.search.news.sources.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.news.common.mviState.FilterState
import com.search.news.common.mviState.Language
import com.search.news.common.mviState.Sort
import com.search.news.sources.domain.sourceListUseCase.GetSourcesUseCase
import com.search.news.sources.domain.model.sourceListModel.NewsSourceErrorResponseDomain
import com.search.news.sources.domain.model.sourceListModel.NewsSourceResponseDomain
import com.search.news.sources.domain.model.sourceListModel.SourceNewsDomain
import com.search.news.sources.domain.model.sourceListModel.SourceResponseDomain
import com.search.news.sources.domain.sourceListUseCase.FindSourcesFromDataBaseUseCaseImpl
import com.search.news.sources.domain.sourceListUseCase.LoadSourcesFromDataBaseUseCaseImpl
import com.search.news.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCaseImpl
import com.search.news.sources.presentation.viewmodel.model.sourseList.NewsSourceErrorResponseVM
import com.search.news.sources.presentation.viewmodel.model.sourseList.NewsSourceResponceVM
import com.search.news.sources.presentation.viewmodel.model.sourseList.SourceNewsVM
import com.search.news.sources.presentation.viewmodel.model.sourseList.SourceResponseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class SourceListViewModel @Inject constructor(
    private val sourcesUseCase: GetSourcesUseCase,
    private val saveInDataBaseSourcesUseCase: SaveInDataBaseSourcesUseCaseImpl,
    private val loadSourcesFromDataBaseUseCase: LoadSourcesFromDataBaseUseCaseImpl,
    private val findSourcesFromDataBaseUseCaseImpl: FindSourcesFromDataBaseUseCaseImpl
) : ViewModel() {
    private val listSourceNews = MutableLiveData<List<SourceNewsVM>>(emptyList())

    private val internalErrorResponce = MutableLiveData<NewsSourceErrorResponseVM>()

    private val networkEroor = MutableLiveData<NewsSourceErrorResponseVM>()


    fun getListSourceNews(): LiveData<List<SourceNewsVM>> = listSourceNews

    fun getInternalErrorResponse(): LiveData<NewsSourceErrorResponseVM> = internalErrorResponce

    fun getNetworkErrorResponce(): LiveData<NewsSourceErrorResponseVM> = networkEroor

    fun getSourcesData(
        apiKey: String,
        page: Int,
        pageSize: Int,
        isOnline: Boolean,
        query: String = "",
        filterState: FilterState

    ) {
        val lang = convertLanguage(filterState.language)
        if (isOnline) {
            viewModelScope.launch(Dispatchers.IO) {
                try {


                    val sourceResponce: SourceResponseVM =
                        mapSourceResponce(apiKey = apiKey, page = page, pageSize = pageSize, language = lang)
                    when (sourceResponce) {
                        is NewsSourceResponceVM -> {

                            val oldValue = listSourceNews.value ?: emptyList()
                            val updateList = sourceResponce.sources + oldValue
                            listSourceNews.postValue(updateList)
                            saveInDataBaseSourcesUseCase.saveSourcesInDb(
                                mapInSourceNewsDomain(
                                    sourceResponce.sources
                                )
                            )

                        }

                        is NewsSourceErrorResponseVM -> {
                            internalErrorResponce.postValue(sourceResponce)
                        }

                    }
                } catch (e: Exception) {
                    if (e is IOException) {
                        networkEroor.postValue(
                            NewsSourceErrorResponseVM(
                                status = "error",
                                code = "Network error",
                                message = e.message
                            )
                        )

                    } else {
                        internalErrorResponce.postValue(
                            NewsSourceErrorResponseVM(
                                status = "error",
                                code = "Internal error",
                                message = e.message
                            )
                        )
                    }
                }
            }
        } else {

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val listSourceNewsVM: List<SourceNewsVM> =
                        loadSourcesFromDataBaseUseCase.loadSourcesFromDB(language = lang).let {
                            mapFromSourceNewsDomain(it)

                        }

                    if (listSourceNewsVM.isEmpty()) {
                        networkEroor.postValue(
                            NewsSourceErrorResponseVM(
                                status = "error",
                                code = "Empty save source",
                                message = "Empty save source"
                            )
                        )
                    } else {
                        listSourceNews.postValue(listSourceNewsVM)
                    }
                } catch (e: Exception) {
                    internalErrorResponce.postValue(
                        NewsSourceErrorResponseVM(
                            status = "error",
                            code = "Internal error",
                            message = e.message
                        )
                    )

                }
            }


        }


    }

    fun findSearchSources(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {


                val list: List<SourceNewsVM> =
                    findSourcesFromDataBaseUseCaseImpl.findSourcesFromDataBase(query).let {
                        mapFromSourceNewsDomain(it)
                    }

                listSourceNews.postValue(list)


            } catch (e:Exception){
                internalErrorResponce.postValue(
                    NewsSourceErrorResponseVM(
                    status = "error",
                    code = "Internal error",
                    message = e.message
                ))
            }
        }
    }

    suspend fun mapSourceResponce(
        apiKey: String,
        page: Int,
        pageSize: Int,
        language: String
    ): SourceResponseVM {

        val sourceResponseDomain: SourceResponseDomain =
            sourcesUseCase.getSources(apiKey = apiKey, page = page, pageSize = pageSize, language = language)
        when (sourceResponseDomain) {
            is NewsSourceResponseDomain -> {
                val listSourceNewsVM: MutableList<SourceNewsVM> = mutableListOf()
                sourceResponseDomain.sources?.map {
                    listSourceNewsVM.add(
                        SourceNewsVM(
                            id = it.id,
                            name = it.name,
                            category = it.category,
                            language = it.language,
                            country = it.country
                        )
                    )
                }
                return NewsSourceResponceVM(sources = listSourceNewsVM)
            }

            is NewsSourceErrorResponseDomain -> {
                return NewsSourceErrorResponseVM(
                    status = sourceResponseDomain.status,
                    code = sourceResponseDomain.code,
                    message = sourceResponseDomain.message
                )
            }
        }
    }

    suspend fun mapInSourceNewsDomain(sourceNewsVMList: List<SourceNewsVM>): List<SourceNewsDomain> {
        return sourceNewsVMList.map {
            SourceNewsDomain(
                id = it.id,
                name = it.name,
                category = it.category,
                language = it.language,
                country = it.country
            )
        }

    }

    suspend fun mapFromSourceNewsDomain(sourceNewsDomain: List<SourceNewsDomain>): List<SourceNewsVM> {
        return sourceNewsDomain.map {
            SourceNewsVM(
                id = it.id,
                name = it.name,
                category = it.category,
                language = it.language,
                country = it.country
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

    fun hasBage(filterState: FilterState): Boolean {
        if (filterState.language == Language.English && filterState.date == null && filterState.sort == Sort.New) {
            return false
        } else {
            return true
        }
    }

    fun clearData(){
        listSourceNews.postValue(emptyList())
    }


}