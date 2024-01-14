package com.aston.astonintensivfinal.saved.presentation.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataSavedListUseCase
import com.aston.astonintensivfinal.saved.presentation.repository.savedListModel.SavedListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavedListViewModel @Inject constructor(private val loadDataSavedListUseCase: LoadDataSavedListUseCase): ViewModel(){
    private val _mutableNewsList: MutableLiveData<List<SavedListModel>> = MutableLiveData()

    val newsList: LiveData<List<SavedListModel>> = _mutableNewsList


    fun mapToSavedListViewModel(news: List<SavedNewsModelDomain>): List<SavedListModel>{
        return news.map { SavedListModel(urlToImage = it.urlToImage, title = it.titleNews, content = it.content, publishedAt = it.publishedAt, source = it.source, description = it.description, url = it.url) }
    }

    fun loadNewsFromRepository(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadDataSavedListUseCase.loadNewsFromDataBase().let {
                  _mutableNewsList.postValue(  mapToSavedListViewModel(it))
                }
            }

        }
    }
}