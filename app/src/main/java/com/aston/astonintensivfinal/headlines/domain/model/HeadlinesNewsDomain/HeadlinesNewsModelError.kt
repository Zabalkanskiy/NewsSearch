package com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain

data class HeadlinesNewsModelError(

    val status: String? = null,

    val code: String? = null,

    val message: String? = null
) : HeadlineNewsResponce()
