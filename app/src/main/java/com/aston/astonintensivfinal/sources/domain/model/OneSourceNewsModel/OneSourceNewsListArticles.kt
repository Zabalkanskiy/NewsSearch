package com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel

import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData

class OneSourceNewsListArticles(val totalResults: Int, val articles: List<OneSourceNewsArticle>): OneSourceNewsDomain()


