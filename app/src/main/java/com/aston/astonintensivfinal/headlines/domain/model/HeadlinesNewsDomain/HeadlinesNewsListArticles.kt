package com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain

data class HeadlinesNewsListArticles(val totalResults: Int?, val articles: List<HeadlinesNewsModelData>):
    HeadlineNewsResponce()