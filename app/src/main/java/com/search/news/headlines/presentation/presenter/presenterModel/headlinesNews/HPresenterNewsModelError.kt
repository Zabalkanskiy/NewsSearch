package com.search.news.headlines.presentation.presenter.presenterModel.headlinesNews

data class HPresenterNewsModelError(

val status: String? = null,

val code: String? = null,

val message: String? = null

) : HeadlinesPresenterNewsResponce()