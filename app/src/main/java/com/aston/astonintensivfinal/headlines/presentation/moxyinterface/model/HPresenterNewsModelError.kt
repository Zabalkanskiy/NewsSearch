package com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model

data class HPresenterNewsModelError(

val status: String? = null,

val code: String? = null,

val message: String? = null

) : HeadlinesPresenterNewsResponce()