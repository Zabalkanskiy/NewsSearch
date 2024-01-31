package com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews

import com.aston.astonintensivfinal.core.data.headlinesmodel.Source

data class HPresenterNewsModelData(

    val source: String? = null,

     val idSource:String,


    val title: String? = null,


    val description: String? = null,


    val url: String? = null,


    val urlToImage: String? = null,


    val publishedAt: String? = null,

    val content: String? = null,

    )