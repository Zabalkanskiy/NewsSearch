package com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain

import com.aston.astonintensivfinal.core.data.headlinesmodel.Source

data class HeadlinesNewsModelData(
    val sourceid: String,

    val sourceName:String,

    val author: String? = null,


    val title: String? = null,


    val description: String? = null,


    val url: String? = null,


    val urlToImage: String? = null,


    val publishedAt: String? = null,

    val content: String? = null,
)
