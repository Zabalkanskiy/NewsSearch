package com.aston.astonintensivfinal.headlines.presentation.moxyinterface

import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HeadlinesView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showData( totalResults: Int?, data: List<HPresenterNewsModelData>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(errorMessage: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTab()
}