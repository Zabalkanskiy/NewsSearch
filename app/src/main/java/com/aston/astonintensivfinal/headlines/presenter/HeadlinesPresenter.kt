package com.aston.astonintensivfinal.headlines.presenter

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.Article
import com.aston.astonintensivfinal.data.ErrorResponce
import com.aston.astonintensivfinal.data.NewApiResponce
import com.aston.astonintensivfinal.headlines.moxyinterface.HeadlinesView
import com.aston.astonintensivfinal.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class HeadlinesPresenter: MvpPresenter<HeadlinesView>() {
    fun loadData() {
        // Здесь можно получить данные, например, из репозитория или сетевого источника.
        // А затем использовать getViewState() для взаимодействия с View.
        // Пример:
        try {
            AstonIntensivApplication.getAstonApplicationContext.headlinesApi.getGeneralHeadlines(apiKey = Utils.NEWSAPIKEY, page = 1)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( {
                    apiResponse ->
                    when (apiResponse) {
                        is NewApiResponce -> {
                            // Обработка результатов запроса
                            val newsApiResponce =  apiResponse as NewApiResponce

                            viewState.showData(newsApiResponce.articles as List<Article>)
                        }
                        is ErrorResponce -> {
                            // Обработка ошибки в запросе
                        }
                    }


                }, {
                    trowable ->
                    // Обработка сетевой ошибки
            })
            val data = "Fetched Data"

          //  viewState.showData(data)

        } catch (e: Exception) {
            viewState.showError(e.message ?: "Unknown error")
        }
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }





}