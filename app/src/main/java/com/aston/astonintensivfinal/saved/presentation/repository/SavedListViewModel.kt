package com.aston.astonintensivfinal.saved.presentation.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.common.mviState.Sort
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataRangeSavedListUseCase
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataSavedListUseCase
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.SearchSavedNewsUseCase
import com.aston.astonintensivfinal.saved.presentation.repository.savedListModel.SavedListModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class SavedListViewModel @Inject constructor(
    private val loadDataSavedListUseCase: LoadDataSavedListUseCase,
    private val loadDataRangeSavedListUseCase: LoadDataRangeSavedListUseCase,
    private val searchSavedNewsUseCase: SearchSavedNewsUseCase
) : ViewModel() {

    private val _mutableNewsList: MutableLiveData<List<SavedListModel>> = MutableLiveData()
    private val _mutableInternalError: MutableLiveData<String> = MutableLiveData()

    val newsList: LiveData<List<SavedListModel>> = _mutableNewsList
    val internalError: LiveData<String> = _mutableInternalError


    fun mapToSavedListViewModel(news: List<SavedNewsModelDomain>): List<SavedListModel> {
        return news.map {
            SavedListModel(
                urlToImage = it.urlToImage,
                title = it.titleNews,
                content = it.content,
                publishedAt = it.publishedAt,
                source = it.source,
                description = it.description,
                url = it.url,
                idSource = it.idSource
            )
        }
    }

    fun loadNewsFromRepository(filterState: FilterState) {
        val lang = convertLanguage(filterState.language)
        var startDate: Date? = null
        var endDate: Date? = null

        if (filterState.date != null) {
            startDate = stringToDate(filterState.date!!.start)
            endDate = stringToDate(filterState.date!!.end)
        }

        if (filterState.date == null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO + coroutineDBExeptionHandler) {
                    loadDataSavedListUseCase.loadNewsFromDataBase(language = lang).let {
                        _mutableNewsList.postValue(mapToSavedListViewModel(it))
                    }
                }

            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.IO + coroutineDBExeptionHandler) {
                    loadDataRangeSavedListUseCase.getRangeNewsFromDB(
                        language = lang,
                        startDate = startDate as Date,
                        endDate = endDate as Date
                    ).let {
                        _mutableNewsList.postValue(mapToSavedListViewModel(it))
                    }
                }
            }
        }
    }

    fun searchNewsFromQuery(filterState: FilterState, query: String) {
        val lang = convertLanguage(filterState.language)
        viewModelScope.launch {
            withContext(Dispatchers.IO + coroutineDBExeptionHandler) {
                searchSavedNewsUseCase.searchSavedNews(language = lang, query = query).let {
                    _mutableNewsList.postValue(mapToSavedListViewModel(it))
                }
            }

        }
    }

    val coroutineDBExeptionHandler = CoroutineExceptionHandler { _, _ ->
        _mutableInternalError.postValue("Internal Error")

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

    fun convertLanguage(language: Language): String {
        return when (language) {
            Language.English -> "en"
            Language.Russian -> "ru"
            Language.Deutsch -> "de"
        }
    }
}