package com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model

import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsModelData

data class HPresenterNewsListArticles( val articles: List<HPresenterNewsModelData>) : HeadlinesPresenterNewsResponce()