package com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel

import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce

data class OneSourceNewsError(
    val status: String,

    val code: String,

    val message: String
): OneSourceNewsDomain()
