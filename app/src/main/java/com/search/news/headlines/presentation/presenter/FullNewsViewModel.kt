package com.search.news.headlines.presentation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.news.headlines.domain.fullNewsUseCases.DeleteFullNewsUseCase
import com.search.news.headlines.domain.fullNewsUseCases.FindNewsInDatabaseUseCase
import com.search.news.headlines.domain.fullNewsUseCases.SaveFullNewsUseCase
import com.search.news.headlines.domain.model.FullNewsDomain.FullNewsDomainModel
import com.search.news.headlines.presentation.presenter.presenterModel.fullNewsPresenter.FullNewsPresenterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FullNewsViewModel@Inject constructor(
    private val saveFullNewsUseCase: SaveFullNewsUseCase,
    private val deleteFullNewsUseCase: DeleteFullNewsUseCase,
    private val findNewsInDatabaseUseCase: FindNewsInDatabaseUseCase
) : ViewModel() {

    private val _newsInDataBase = MutableSharedFlow<Boolean>()
    val newsInDataBase: Flow<Boolean> = _newsInDataBase.asSharedFlow()


    fun saveNews(news: FullNewsPresenterModel) {
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            saveFullNewsUseCase(mapToFullNewsDomainModel(news))
            _newsInDataBase.emit(true)

        }
    }

    fun deleteNews(news: FullNewsPresenterModel) {
        viewModelScope.launch((Dispatchers.IO + SupervisorJob())) {
            deleteFullNewsUseCase(mapToFullNewsDomainModel(news))
            _newsInDataBase.emit(false)

        }
    }

    fun checkIfNewsExists(title: String, urlToImage: String) {
        viewModelScope.launch {
         val countNews =   withContext(Dispatchers.IO) {
             findNewsInDatabaseUseCase(title, urlToImage)

            }

            _newsInDataBase.emit(countNews)
        }

    }
    fun mapToFullNewsDomainModel(fullNewsPresenterModel: FullNewsPresenterModel): FullNewsDomainModel {
        return FullNewsDomainModel(
            urlToImage = fullNewsPresenterModel.urlToImage,
            title = fullNewsPresenterModel.title,
            content = fullNewsPresenterModel.content,
            publishedAt = fullNewsPresenterModel.publishedAt,
            sourceName = fullNewsPresenterModel.source,
            description = fullNewsPresenterModel.description,
            url = fullNewsPresenterModel.url,
            idSource = fullNewsPresenterModel.idSource

        )
    }

}