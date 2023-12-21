package com.aston.astonintensivfinal.headlines.moxyinterface

import com.aston.astonintensivfinal.data.Article
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HeadlinesView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showData(data: List<Article>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(errorMessage: String)
}