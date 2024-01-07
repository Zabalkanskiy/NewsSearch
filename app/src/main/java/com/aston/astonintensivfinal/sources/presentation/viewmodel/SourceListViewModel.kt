package com.aston.astonintensivfinal.sources.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.astonintensivfinal.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.sources.domain.GetSourcesUseCase
import com.aston.astonintensivfinal.sources.domain.model.NewsSourceErrorResponseDomain
import com.aston.astonintensivfinal.sources.domain.model.NewsSourceResponseDomain
import com.aston.astonintensivfinal.sources.domain.model.SourceResponseDomain
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.NewsSourceErrorResponseVM
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.NewsSourceResponceVM
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.SourceNewsVM
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.SourceResponseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SourceListViewModel @Inject constructor(private val sourcesUseCase: GetSourcesUseCase) : ViewModel() {
    private val listSourceNews = MutableLiveData<List<SourceNewsVM>>(emptyList())

    private val errorResponce = MutableLiveData<NewsSourceErrorResponseVM>()



    fun getListSourceNews(): LiveData<List<SourceNewsVM>> = listSourceNews

    fun getErrorResponse(): LiveData<NewsSourceErrorResponseVM> = errorResponce

    fun getSourcesData(
        apiKey: String,
        page: Int,
        pageSize: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {


                val sourceResponce: SourceResponseVM =
                    mapSourceResponce(apiKey = apiKey, page = page, pageSize = pageSize)
                when (sourceResponce) {
                    is NewsSourceResponceVM -> {

                        val oldValue = listSourceNews.value ?: emptyList()
                        val updateList = sourceResponce.sources + oldValue
                        listSourceNews.postValue(updateList)

                    }

                    is NewsSourceErrorResponseVM -> {
                        errorResponce.postValue(sourceResponce)
                    }

                }
            } catch (e: Exception) {
                errorResponce.postValue(NewsSourceErrorResponseVM(status = "error", code = "view model", message = e.message))
            }
        }


    }

    suspend fun mapSourceResponce(
        apiKey: String,
        page: Int,
        pageSize: Int
    ): SourceResponseVM {

        val sourceResponseDomain: SourceResponseDomain =
            sourcesUseCase.getSources(apiKey = apiKey, page = page, pageSize = pageSize)
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
                return NewsSourceErrorResponseVM(status = sourceResponseDomain.status, code = sourceResponseDomain.code, message = sourceResponseDomain.message)
            }
        }
    }


}