package com.aston.astonintensivfinal.headlines.presentation.moxyinterface

import com.aston.astonintensivfinal.data.Article
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model.HPresenterNewsModelData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HeadlinesView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showData(data: List<HPresenterNewsModelData>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(errorMessage: String)
}