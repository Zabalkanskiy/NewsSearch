package com.search.news.headlines.presentation.moxyinterface

import com.search.news.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HeadlinesView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showData( totalResults: Int?, data: List<HPresenterNewsModelData>)



    @StateStrategyType(AddToEndSingleStrategy::class)
    fun networkError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun internalError()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTab()
}